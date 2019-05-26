package com.google.ar.sceneform.rendering;

import android.support.annotation.Nullable;
import com.google.android.filament.Engine;
import com.google.android.filament.IndexBuffer;
import com.google.android.filament.IndexBuffer.Builder.IndexType;
import com.google.android.filament.VertexBuffer;
import com.google.android.filament.VertexBuffer.AttributeType;
import com.google.android.filament.VertexBuffer.VertexAttribute;
import com.google.ar.sceneform.math.MathHelper;
import com.google.ar.sceneform.math.Matrix;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Vertex.UvCoordinate;
import com.google.ar.sceneform.utilities.AndroidPreconditions;
import com.google.ar.sceneform.utilities.Preconditions;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class RenderableDefinition {
    private static final int BYTES_PER_FLOAT = 4;
    private static final int COLOR_SIZE = 4;
    private static final int POSITION_SIZE = 3;
    private static final int TANGENTS_SIZE = 4;
    private static final int UV_SIZE = 2;
    private List<Submesh> submeshes;
    private List<Vertex> vertices;

    public static final class Builder {
        @Nullable
        private List<Submesh> submeshes = new ArrayList();
        @Nullable
        private List<Vertex> vertices;

        public Builder setVertices(List<Vertex> vertices) {
            this.vertices = vertices;
            return this;
        }

        public Builder setSubmeshes(List<Submesh> submeshes) {
            this.submeshes = submeshes;
            return this;
        }

        public RenderableDefinition build() {
            return new RenderableDefinition();
        }
    }

    public static class Submesh {
        private Material material;
        @Nullable
        private String name;
        private List<Integer> triangleIndices;

        public static final class Builder {
            @Nullable
            private Material material;
            @Nullable
            private String name;
            @Nullable
            private List<Integer> triangleIndices;

            public Builder setTriangleIndices(List<Integer> triangleIndices) {
                this.triangleIndices = triangleIndices;
                return this;
            }

            public Builder setName(String name) {
                this.name = name;
                return this;
            }

            public Builder setMaterial(Material material) {
                this.material = material;
                return this;
            }

            public Submesh build() {
                return new Submesh();
            }
        }

        public void setTriangleIndices(List<Integer> triangleIndices) {
            this.triangleIndices = triangleIndices;
        }

        public List<Integer> getTriangleIndices() {
            return this.triangleIndices;
        }

        public void setMaterial(Material material) {
            this.material = material;
        }

        public Material getMaterial() {
            return this.material;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Nullable
        public String getName() {
            return this.name;
        }

        private Submesh(Builder builder) {
            this.triangleIndices = (List) Preconditions.checkNotNull(builder.triangleIndices);
            this.material = (Material) Preconditions.checkNotNull(builder.material);
            this.name = builder.name;
        }

        public static Builder builder() {
            return new Builder();
        }
    }

    public void setVertices(List<Vertex> vertices) {
        this.vertices = vertices;
    }

    List<Vertex> getVertices() {
        return this.vertices;
    }

    public void setSubmeshes(List<Submesh> submeshes) {
        this.submeshes = submeshes;
    }

    List<Submesh> getSubmeshes() {
        return this.submeshes;
    }

    void applyDefinitionToData(RenderableInternalData data, ArrayList<Material> materialBindings) {
        AndroidPreconditions.checkUiThread();
        applyDefinitionToDataIndexBuffer(data);
        applyDefinitionToDataVertexBuffer(data);
        int indexStart = 0;
        materialBindings.clear();
        for (int i = 0; i < this.submeshes.size(); i++) {
            MeshData meshData;
            Submesh submesh = (Submesh) this.submeshes.get(i);
            if (i < data.getMeshes().size()) {
                meshData = (MeshData) data.getMeshes().get(i);
            } else {
                meshData = new MeshData();
                data.getMeshes().add(meshData);
            }
            meshData.indexStart = indexStart;
            meshData.indexEnd = submesh.getTriangleIndices().size() + indexStart;
            indexStart = meshData.indexEnd;
            materialBindings.add(submesh.getMaterial());
        }
        while (data.getMeshes().size() > this.submeshes.size()) {
            data.getMeshes().remove(data.getMeshes().size() - 1);
        }
    }

    private void applyDefinitionToDataIndexBuffer(RenderableInternalData data) {
        int i;
        List<Integer> triangleIndices;
        int j;
        IndexBuffer indexBuffer;
        Engine engine;
        int numIndices = 0;
        for (int i2 = 0; i2 < this.submeshes.size(); i2++) {
            numIndices += ((Submesh) this.submeshes.get(i2)).getTriangleIndices().size();
        }
        IntBuffer rawIndexBuffer = data.getRawIndexBuffer();
        if (rawIndexBuffer != null) {
            if (rawIndexBuffer.capacity() >= numIndices) {
                rawIndexBuffer.rewind();
                for (i = 0; i < this.submeshes.size(); i++) {
                    triangleIndices = ((Submesh) this.submeshes.get(i)).getTriangleIndices();
                    for (j = 0; j < triangleIndices.size(); j++) {
                        rawIndexBuffer.put(((Integer) triangleIndices.get(j)).intValue());
                    }
                }
                rawIndexBuffer.rewind();
                indexBuffer = data.getIndexBuffer();
                engine = EngineInstance.getEngine();
                if (indexBuffer == null || indexBuffer.getIndexCount() < numIndices) {
                    if (indexBuffer != null) {
                        engine.destroyIndexBuffer(indexBuffer);
                    }
                    indexBuffer = new com.google.android.filament.IndexBuffer.Builder().indexCount(numIndices).bufferType(IndexType.UINT).build(engine);
                    data.setIndexBuffer(indexBuffer);
                }
                indexBuffer.setBuffer(engine, rawIndexBuffer, 0, numIndices);
            }
        }
        rawIndexBuffer = IntBuffer.allocate(numIndices);
        data.setRawIndexBuffer(rawIndexBuffer);
        for (i = 0; i < this.submeshes.size(); i++) {
            triangleIndices = ((Submesh) this.submeshes.get(i)).getTriangleIndices();
            for (j = 0; j < triangleIndices.size(); j++) {
                rawIndexBuffer.put(((Integer) triangleIndices.get(j)).intValue());
            }
        }
        rawIndexBuffer.rewind();
        indexBuffer = data.getIndexBuffer();
        engine = EngineInstance.getEngine();
        if (indexBuffer != null) {
            engine.destroyIndexBuffer(indexBuffer);
        }
        indexBuffer = new com.google.android.filament.IndexBuffer.Builder().indexCount(numIndices).bufferType(IndexType.UINT).build(engine);
        data.setIndexBuffer(indexBuffer);
        indexBuffer.setBuffer(engine, rawIndexBuffer, 0, numIndices);
    }

    private void applyDefinitionToDataVertexBuffer(RenderableInternalData data) {
        RenderableInternalData renderableInternalData = data;
        if (this.vertices.isEmpty()) {
            throw new IllegalArgumentException("RenderableDescription must have at least one vertex.");
        }
        RenderableDefinition renderableDefinition;
        Buffer positionBuffer;
        Buffer tangentsBuffer;
        Buffer uvBuffer;
        Buffer colorBuffer;
        Vector3 minAabb;
        Vector3 maxAabb;
        Vector3 firstPosition;
        Vertex vertex;
        Vector3 position;
        UvCoordinate uvCoordinate;
        Color color;
        Vector3 centerAabb;
        Engine engine;
        int bufferIndex;
        Buffer colorBuffer2;
        Buffer uvBuffer2;
        int numVertices = renderableDefinition.vertices.size();
        int i = 0;
        Vertex firstVertex = (Vertex) renderableDefinition.vertices.get(0);
        EnumSet<VertexAttribute> descriptionAttributes = EnumSet.of(VertexAttribute.POSITION);
        if (firstVertex.getNormal() != null) {
            descriptionAttributes.add(VertexAttribute.TANGENTS);
        }
        if (firstVertex.getUvCoordinate() != null) {
            descriptionAttributes.add(VertexAttribute.UV0);
        }
        if (firstVertex.getColor() != null) {
            descriptionAttributes.add(VertexAttribute.COLOR);
        }
        VertexBuffer vertexBuffer = data.getVertexBuffer();
        boolean createVertexBuffer = true;
        if (vertexBuffer != null) {
            boolean z;
            EnumSet<VertexAttribute> oldAttributes = EnumSet.of(VertexAttribute.POSITION);
            if (data.getRawTangentsBuffer() != null) {
                oldAttributes.add(VertexAttribute.TANGENTS);
            }
            if (data.getRawUvBuffer() != null) {
                oldAttributes.add(VertexAttribute.UV0);
            }
            if (data.getRawColorBuffer() != null) {
                oldAttributes.add(VertexAttribute.COLOR);
            }
            if (oldAttributes.equals(descriptionAttributes)) {
                if (vertexBuffer.getVertexCount() >= numVertices) {
                    z = false;
                    createVertexBuffer = z;
                    if (createVertexBuffer) {
                        EngineInstance.getEngine().destroyVertexBuffer(vertexBuffer);
                    }
                }
            }
            z = true;
            createVertexBuffer = z;
            if (createVertexBuffer) {
                EngineInstance.getEngine().destroyVertexBuffer(vertexBuffer);
            }
        }
        if (createVertexBuffer) {
            vertexBuffer = createVertexBuffer(numVertices, descriptionAttributes);
            renderableInternalData.setVertexBuffer(vertexBuffer);
        }
        Buffer positionBuffer2 = data.getRawPositionBuffer();
        if (positionBuffer2 != null) {
            if (positionBuffer2.capacity() >= numVertices * 3) {
                positionBuffer2.rewind();
                positionBuffer = positionBuffer2;
                positionBuffer2 = data.getRawTangentsBuffer();
                if (!descriptionAttributes.contains(VertexAttribute.TANGENTS) && (positionBuffer2 == null || positionBuffer2.capacity() < numVertices * 4)) {
                    positionBuffer2 = FloatBuffer.allocate(numVertices * 4);
                    renderableInternalData.setRawTangentsBuffer(positionBuffer2);
                } else if (positionBuffer2 != null) {
                    positionBuffer2.rewind();
                }
                tangentsBuffer = positionBuffer2;
                positionBuffer2 = data.getRawUvBuffer();
                if (!descriptionAttributes.contains(VertexAttribute.UV0) && (positionBuffer2 == null || positionBuffer2.capacity() < numVertices * 2)) {
                    positionBuffer2 = FloatBuffer.allocate(numVertices * 2);
                    renderableInternalData.setRawUvBuffer(positionBuffer2);
                } else if (positionBuffer2 != null) {
                    positionBuffer2.rewind();
                }
                uvBuffer = positionBuffer2;
                positionBuffer2 = data.getRawColorBuffer();
                if (!descriptionAttributes.contains(VertexAttribute.COLOR) && (positionBuffer2 == null || positionBuffer2.capacity() < numVertices * 4)) {
                    positionBuffer2 = FloatBuffer.allocate(numVertices * 4);
                    renderableInternalData.setRawColorBuffer(positionBuffer2);
                } else if (positionBuffer2 != null) {
                    positionBuffer2.rewind();
                }
                colorBuffer = positionBuffer2;
                minAabb = new Vector3();
                maxAabb = new Vector3();
                firstPosition = firstVertex.getPosition();
                minAabb.set(firstPosition);
                maxAabb.set(firstPosition);
                while (i < renderableDefinition.vertices.size()) {
                    vertex = (Vertex) renderableDefinition.vertices.get(i);
                    position = vertex.getPosition();
                    Vertex firstVertex2 = firstVertex;
                    minAabb.set(Vector3.min(minAabb, position));
                    maxAabb.set(Vector3.max(maxAabb, position));
                    addVector3ToBuffer(position, positionBuffer);
                    if (tangentsBuffer == null) {
                        firstVertex = vertex.getNormal();
                        if (firstVertex == null) {
                            addQuaternionToBuffer(normalToTangent(firstVertex), tangentsBuffer);
                        } else {
                            Vertex normal = firstVertex;
                            throw new IllegalArgumentException("Missing normal: If any Vertex in a RenderableDescription has a normal, all vertices must have one.");
                        }
                    }
                    if (uvBuffer != null) {
                        uvCoordinate = vertex.getUvCoordinate();
                        if (uvCoordinate == null) {
                            addUvToBuffer(uvCoordinate, uvBuffer);
                        } else {
                            throw new IllegalArgumentException("Missing UV Coordinate: If any Vertex in a RenderableDescription has a UV Coordinate, all vertices must have one.");
                        }
                    }
                    if (colorBuffer != null) {
                        color = vertex.getColor();
                        if (color == null) {
                            addColorToBuffer(color, colorBuffer);
                        } else {
                            throw new IllegalArgumentException("Missing Color: If any Vertex in a RenderableDescription has a Color, all vertices must have one.");
                        }
                    }
                    i++;
                    firstVertex = firstVertex2;
                    renderableDefinition = this;
                }
                position = Vector3.subtract(maxAabb, minAabb).scaled(0.5f);
                centerAabb = Vector3.add(minAabb, position);
                renderableInternalData.setExtentsAabb(position);
                renderableInternalData.setCenterAabb(centerAabb);
                if (vertexBuffer == null) {
                    engine = EngineInstance.getEngine();
                    positionBuffer.rewind();
                    bufferIndex = 0;
                    colorBuffer2 = colorBuffer;
                    uvBuffer2 = uvBuffer;
                    vertexBuffer.setBufferAt(engine, 0, positionBuffer, 0, numVertices * 3);
                    if (tangentsBuffer != null) {
                        tangentsBuffer.rewind();
                        bufferIndex = 0 + 1;
                        vertexBuffer.setBufferAt(engine, bufferIndex, tangentsBuffer, 0, numVertices * 4);
                    }
                    if (uvBuffer2 != null) {
                        uvBuffer2.rewind();
                        bufferIndex++;
                        vertexBuffer.setBufferAt(engine, bufferIndex, uvBuffer2, 0, numVertices * 2);
                    }
                    if (colorBuffer2 != null) {
                        colorBuffer2.rewind();
                        vertexBuffer.setBufferAt(engine, bufferIndex + 1, colorBuffer2, 0, numVertices * 4);
                    }
                }
                Vector3 vector3 = maxAabb;
                Vector3 vector32 = minAabb;
                colorBuffer2 = colorBuffer;
                uvBuffer2 = uvBuffer;
                throw new AssertionError("VertexBuffer is null.");
            }
        }
        positionBuffer2 = FloatBuffer.allocate(numVertices * 3);
        renderableInternalData.setRawPositionBuffer(positionBuffer2);
        positionBuffer = positionBuffer2;
        positionBuffer2 = data.getRawTangentsBuffer();
        if (!descriptionAttributes.contains(VertexAttribute.TANGENTS)) {
        }
        if (positionBuffer2 != null) {
            positionBuffer2.rewind();
        }
        tangentsBuffer = positionBuffer2;
        positionBuffer2 = data.getRawUvBuffer();
        if (!descriptionAttributes.contains(VertexAttribute.UV0)) {
        }
        if (positionBuffer2 != null) {
            positionBuffer2.rewind();
        }
        uvBuffer = positionBuffer2;
        positionBuffer2 = data.getRawColorBuffer();
        if (!descriptionAttributes.contains(VertexAttribute.COLOR)) {
        }
        if (positionBuffer2 != null) {
            positionBuffer2.rewind();
        }
        colorBuffer = positionBuffer2;
        minAabb = new Vector3();
        maxAabb = new Vector3();
        firstPosition = firstVertex.getPosition();
        minAabb.set(firstPosition);
        maxAabb.set(firstPosition);
        while (i < renderableDefinition.vertices.size()) {
            vertex = (Vertex) renderableDefinition.vertices.get(i);
            position = vertex.getPosition();
            Vertex firstVertex22 = firstVertex;
            minAabb.set(Vector3.min(minAabb, position));
            maxAabb.set(Vector3.max(maxAabb, position));
            addVector3ToBuffer(position, positionBuffer);
            if (tangentsBuffer == null) {
            } else {
                firstVertex = vertex.getNormal();
                if (firstVertex == null) {
                    Vertex normal2 = firstVertex;
                    throw new IllegalArgumentException("Missing normal: If any Vertex in a RenderableDescription has a normal, all vertices must have one.");
                }
                addQuaternionToBuffer(normalToTangent(firstVertex), tangentsBuffer);
            }
            if (uvBuffer != null) {
                uvCoordinate = vertex.getUvCoordinate();
                if (uvCoordinate == null) {
                    throw new IllegalArgumentException("Missing UV Coordinate: If any Vertex in a RenderableDescription has a UV Coordinate, all vertices must have one.");
                }
                addUvToBuffer(uvCoordinate, uvBuffer);
            }
            if (colorBuffer != null) {
                color = vertex.getColor();
                if (color == null) {
                    throw new IllegalArgumentException("Missing Color: If any Vertex in a RenderableDescription has a Color, all vertices must have one.");
                }
                addColorToBuffer(color, colorBuffer);
            }
            i++;
            firstVertex = firstVertex22;
            renderableDefinition = this;
        }
        position = Vector3.subtract(maxAabb, minAabb).scaled(0.5f);
        centerAabb = Vector3.add(minAabb, position);
        renderableInternalData.setExtentsAabb(position);
        renderableInternalData.setCenterAabb(centerAabb);
        if (vertexBuffer == null) {
            Vector3 vector33 = maxAabb;
            Vector3 vector322 = minAabb;
            colorBuffer2 = colorBuffer;
            uvBuffer2 = uvBuffer;
            throw new AssertionError("VertexBuffer is null.");
        }
        engine = EngineInstance.getEngine();
        positionBuffer.rewind();
        bufferIndex = 0;
        colorBuffer2 = colorBuffer;
        uvBuffer2 = uvBuffer;
        vertexBuffer.setBufferAt(engine, 0, positionBuffer, 0, numVertices * 3);
        if (tangentsBuffer != null) {
            tangentsBuffer.rewind();
            bufferIndex = 0 + 1;
            vertexBuffer.setBufferAt(engine, bufferIndex, tangentsBuffer, 0, numVertices * 4);
        }
        if (uvBuffer2 != null) {
            uvBuffer2.rewind();
            bufferIndex++;
            vertexBuffer.setBufferAt(engine, bufferIndex, uvBuffer2, 0, numVertices * 2);
        }
        if (colorBuffer2 != null) {
            colorBuffer2.rewind();
            vertexBuffer.setBufferAt(engine, bufferIndex + 1, colorBuffer2, 0, numVertices * 4);
        }
    }

    private RenderableDefinition(Builder builder) {
        this.vertices = (List) Preconditions.checkNotNull(builder.vertices);
        this.submeshes = (List) Preconditions.checkNotNull(builder.submeshes);
    }

    public static Builder builder() {
        return new Builder();
    }

    private static VertexBuffer createVertexBuffer(int vertexCount, EnumSet<VertexAttribute> attributes) {
        com.google.android.filament.VertexBuffer.Builder builder = new com.google.android.filament.VertexBuffer.Builder();
        builder.vertexCount(vertexCount).bufferCount(attributes.size());
        int bufferIndex = 0;
        builder.attribute(VertexAttribute.POSITION, 0, AttributeType.FLOAT3, 0, 12);
        if (attributes.contains(VertexAttribute.TANGENTS)) {
            bufferIndex = 0 + 1;
            builder.attribute(VertexAttribute.TANGENTS, bufferIndex, AttributeType.FLOAT4, 0, 16);
        }
        if (attributes.contains(VertexAttribute.UV0)) {
            bufferIndex++;
            builder.attribute(VertexAttribute.UV0, bufferIndex, AttributeType.FLOAT2, 0, 8);
        }
        if (attributes.contains(VertexAttribute.COLOR)) {
            builder.attribute(VertexAttribute.COLOR, bufferIndex + 1, AttributeType.FLOAT4, 0, 16);
        }
        return builder.build(EngineInstance.getEngine());
    }

    private static void addVector3ToBuffer(Vector3 vector3, FloatBuffer buffer) {
        buffer.put(vector3.f120x);
        buffer.put(vector3.f121y);
        buffer.put(vector3.f122z);
    }

    private static void addUvToBuffer(UvCoordinate uvCoordinate, FloatBuffer buffer) {
        buffer.put(uvCoordinate.f130x);
        buffer.put(uvCoordinate.f131y);
    }

    private static void addQuaternionToBuffer(Quaternion quaternion, FloatBuffer buffer) {
        buffer.put(quaternion.f117x);
        buffer.put(quaternion.f118y);
        buffer.put(quaternion.f119z);
        buffer.put(quaternion.f116w);
    }

    private static void addColorToBuffer(Color color, FloatBuffer buffer) {
        buffer.put(color.f126r);
        buffer.put(color.f125g);
        buffer.put(color.f124b);
        buffer.put(color.f123a);
    }

    private static Quaternion normalToTangent(Vector3 normal) {
        Vector3 bitangent;
        Vector3 tangent = Vector3.cross(Vector3.up(), normal);
        if (MathHelper.almostEqualRelativeAndAbs(Vector3.dot(tangent, tangent), 0.0f)) {
            bitangent = Vector3.cross(normal, Vector3.right()).normalized();
            tangent = Vector3.cross(bitangent, normal).normalized();
        } else {
            tangent.set(tangent.normalized());
            bitangent = Vector3.cross(normal, tangent).normalized();
        }
        Matrix orientation = new Matrix();
        orientation.data[0] = tangent.f120x;
        orientation.data[1] = tangent.f121y;
        orientation.data[2] = tangent.f122z;
        orientation.data[4] = bitangent.f120x;
        orientation.data[5] = bitangent.f121y;
        orientation.data[6] = bitangent.f122z;
        orientation.data[8] = normal.f120x;
        orientation.data[9] = normal.f121y;
        orientation.data[10] = normal.f122z;
        return orientation.extractQuaternion();
    }
}
