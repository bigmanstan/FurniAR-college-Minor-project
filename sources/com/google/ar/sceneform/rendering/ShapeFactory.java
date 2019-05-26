package com.google.ar.sceneform.rendering;

import android.support.annotation.RequiresApi;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.RenderableDefinition.Submesh;
import com.google.ar.sceneform.rendering.Vertex.Builder;
import com.google.ar.sceneform.rendering.Vertex.UvCoordinate;
import com.google.ar.sceneform.utilities.AndroidPreconditions;
import java.util.ArrayList;
import java.util.Arrays;

@RequiresApi(api = 24)
public final class ShapeFactory {
    private static final int COORDS_PER_TRIANGLE = 3;
    private static final String TAG = ShapeFactory.class.getSimpleName();

    public static ModelRenderable makeCube(Vector3 size, Vector3 center, Material material) {
        Vector3 vector3 = center;
        AndroidPreconditions.checkMinAndroidApiLevel();
        Vector3 extents = size.scaled(0.5f);
        Vector3 p0 = Vector3.add(vector3, new Vector3(-extents.f120x, -extents.f121y, extents.f122z));
        Vector3 p1 = Vector3.add(vector3, new Vector3(extents.f120x, -extents.f121y, extents.f122z));
        Vector3 p2 = Vector3.add(vector3, new Vector3(extents.f120x, -extents.f121y, -extents.f122z));
        Vector3 p3 = Vector3.add(vector3, new Vector3(-extents.f120x, -extents.f121y, -extents.f122z));
        Vector3 p4 = Vector3.add(vector3, new Vector3(-extents.f120x, extents.f121y, extents.f122z));
        Vector3 p5 = Vector3.add(vector3, new Vector3(extents.f120x, extents.f121y, extents.f122z));
        Vector3 p6 = Vector3.add(vector3, new Vector3(extents.f120x, extents.f121y, -extents.f122z));
        Vector3 p7 = Vector3.add(vector3, new Vector3(-extents.f120x, extents.f121y, -extents.f122z));
        Vector3 up = Vector3.up();
        Vector3 down = Vector3.down();
        Vector3 front = Vector3.forward();
        Vector3 back = Vector3.back();
        vector3 = Vector3.left();
        Vector3 right = Vector3.right();
        UvCoordinate uv00 = new UvCoordinate(0.0f, 0.0f);
        Vector3 up2 = up;
        UvCoordinate uv10 = new UvCoordinate(1.0f, 0.0f);
        UvCoordinate uv01 = new UvCoordinate(0.0f, 1.0f);
        UvCoordinate uv11 = new UvCoordinate(1.0f, 1.0f);
        Vertex[] vertexArr = new Vertex[24];
        Vector3 right2 = right;
        vertexArr[0] = Vertex.builder().setPosition(p0).setNormal(down).setUvCoordinate(uv01).build();
        Vector3 back2 = back;
        vertexArr[1] = Vertex.builder().setPosition(p1).setNormal(down).setUvCoordinate(uv11).build();
        UvCoordinate uv102 = uv10;
        vertexArr[2] = Vertex.builder().setPosition(p2).setNormal(down).setUvCoordinate(uv102).build();
        Builder normal = Vertex.builder().setPosition(p3).setNormal(down);
        UvCoordinate uv002 = uv00;
        vertexArr[3] = normal.setUvCoordinate(uv002).build();
        vertexArr[4] = Vertex.builder().setPosition(p7).setNormal(vector3).setUvCoordinate(uv01).build();
        vertexArr[5] = Vertex.builder().setPosition(p4).setNormal(vector3).setUvCoordinate(uv11).build();
        Vector3 p22 = p2;
        vertexArr[6] = Vertex.builder().setPosition(p0).setNormal(vector3).setUvCoordinate(uv102).build();
        vertexArr[7] = Vertex.builder().setPosition(p3).setNormal(vector3).setUvCoordinate(uv002).build();
        vertexArr[8] = Vertex.builder().setPosition(p4).setNormal(front).setUvCoordinate(uv01).build();
        vertexArr[9] = Vertex.builder().setPosition(p5).setNormal(front).setUvCoordinate(uv11).build();
        vertexArr[10] = Vertex.builder().setPosition(p1).setNormal(front).setUvCoordinate(uv102).build();
        vertexArr[11] = Vertex.builder().setPosition(p0).setNormal(front).setUvCoordinate(uv002).build();
        p2 = back2;
        vertexArr[12] = Vertex.builder().setPosition(p6).setNormal(p2).setUvCoordinate(uv01).build();
        vertexArr[13] = Vertex.builder().setPosition(p7).setNormal(p2).setUvCoordinate(uv11).build();
        vertexArr[14] = Vertex.builder().setPosition(p3).setNormal(p2).setUvCoordinate(uv102).build();
        vector3 = p22;
        vertexArr[15] = Vertex.builder().setPosition(vector3).setNormal(p2).setUvCoordinate(uv002).build();
        p0 = right2;
        vertexArr[16] = Vertex.builder().setPosition(p5).setNormal(p0).setUvCoordinate(uv01).build();
        vertexArr[17] = Vertex.builder().setPosition(p6).setNormal(p0).setUvCoordinate(uv11).build();
        vertexArr[18] = Vertex.builder().setPosition(vector3).setNormal(p0).setUvCoordinate(uv102).build();
        vertexArr[19] = Vertex.builder().setPosition(p1).setNormal(p0).setUvCoordinate(uv002).build();
        vector3 = up2;
        vertexArr[20] = Vertex.builder().setPosition(p7).setNormal(vector3).setUvCoordinate(uv01).build();
        vertexArr[21] = Vertex.builder().setPosition(p6).setNormal(vector3).setUvCoordinate(uv11).build();
        vertexArr[22] = Vertex.builder().setPosition(p5).setNormal(vector3).setUvCoordinate(uv102).build();
        vertexArr[23] = Vertex.builder().setPosition(p4).setNormal(vector3).setUvCoordinate(uv002).build();
        ArrayList<Vertex> vertices = new ArrayList(Arrays.asList(vertexArr));
        ArrayList<Integer> triangleIndices = new ArrayList(36);
        int i = 0;
        while (true) {
            UvCoordinate uv012 = uv01;
            if (i >= 6) {
                break;
            }
            triangleIndices.add(Integer.valueOf((i * 4) + 3));
            triangleIndices.add(Integer.valueOf((i * 4) + 1));
            triangleIndices.add(Integer.valueOf((i * 4) + 0));
            triangleIndices.add(Integer.valueOf((i * 4) + 3));
            triangleIndices.add(Integer.valueOf((i * 4) + 2));
            triangleIndices.add(Integer.valueOf((i * 4) + 1));
            i++;
            uv01 = uv012;
        }
        Submesh submesh = Submesh.builder().setTriangleIndices(triangleIndices).setMaterial(material).build();
        RenderableDefinition renderableDefinition = RenderableDefinition.builder().setVertices(vertices).setSubmeshes(Arrays.asList(new Submesh[]{submesh})).build();
        try {
            ModelRenderable result = (ModelRenderable) ((ModelRenderable.Builder) ModelRenderable.builder().setSource(renderableDefinition)).build().get();
            if (result != null) {
                return result;
            }
            throw new AssertionError("Error creating renderable.");
        } catch (Exception ex) {
            RenderableDefinition renderableDefinition2 = renderableDefinition;
            throw new AssertionError("Error creating renderable.", ex);
        }
    }

    public static ModelRenderable makeSphere(float radius, Vector3 center, Material material) {
        int stacks;
        int slices;
        float pi;
        float f;
        Vector3 vector3;
        AndroidPreconditions.checkMinAndroidApiLevel();
        int stacks2 = 24;
        int slices2 = 24;
        ArrayList<Vertex> vertices = new ArrayList(602);
        float pi2 = 3.1415927f;
        float doublePi = 3.1415927f * 2.0f;
        int stack = 0;
        while (true) {
            int i = 24;
            if (stack > 24) {
                break;
            }
            float f2 = 24.0f;
            float phi = (((float) stack) * pi2) / 24.0f;
            float sinPhi = (float) Math.sin((double) phi);
            float cosPhi = (float) Math.cos((double) phi);
            int slice = 0;
            while (slice <= i) {
                float theta = (((float) (slice == i ? 0 : slice)) * doublePi) / f2;
                stacks = stacks2;
                float cosTheta = (float) Math.cos((double) theta);
                Vector3 position = new Vector3(sinPhi * cosTheta, cosPhi, sinPhi * ((float) Math.sin((double) theta))).scaled(radius);
                slices = slices2;
                pi = pi2;
                vertices.add(Vertex.builder().setPosition(Vector3.add(position, center)).setNormal(position.normalized()).setUvCoordinate(new UvCoordinate(1.0f - (((float) slice) / 24.0f), 1.0f - (((float) stack) / 24.0f))).build());
                slice++;
                stacks2 = stacks;
                f2 = 24.0f;
                slices2 = slices;
                pi2 = pi;
                i = 24;
            }
            f = radius;
            vector3 = center;
            stacks = stacks2;
            slices = slices2;
            pi = pi2;
            stack++;
        }
        f = radius;
        vector3 = center;
        stacks = stacks2;
        slices = slices2;
        pi = pi2;
        ArrayList<Integer> triangleIndices = new ArrayList((vertices.size() * 2) * 3);
        int v = 0;
        int stack2 = 0;
        while (true) {
            int i2 = 24;
            if (stack2 >= 24) {
                break;
            }
            int i3;
            slice = 0;
            while (slice < i2) {
                boolean topCap = stack2 == 0;
                boolean bottomCap = stack2 == 23;
                int next = slice + 1;
                if (!topCap) {
                    triangleIndices.add(Integer.valueOf(v + slice));
                    triangleIndices.add(Integer.valueOf(v + next));
                    triangleIndices.add(Integer.valueOf(((v + slice) + 24) + 1));
                }
                if (bottomCap) {
                    i3 = 24;
                } else {
                    triangleIndices.add(Integer.valueOf(v + next));
                    i3 = 24;
                    triangleIndices.add(Integer.valueOf(((v + next) + 24) + 1));
                    triangleIndices.add(Integer.valueOf(((v + slice) + 24) + 1));
                }
                slice++;
                i2 = i3;
            }
            i3 = i2;
            v += 25;
            stack2++;
        }
        Submesh submesh = Submesh.builder().setTriangleIndices(triangleIndices).setMaterial(material).build();
        try {
            ModelRenderable result = (ModelRenderable) ((ModelRenderable.Builder) ModelRenderable.builder().setSource(RenderableDefinition.builder().setVertices(vertices).setSubmeshes(Arrays.asList(new Submesh[]{submesh})).build())).build().get();
            if (result != null) {
                return result;
            }
            throw new AssertionError("Error creating renderable.");
        } catch (Exception ex) {
            throw new AssertionError("Error creating renderable.", ex);
        }
    }

    public static ModelRenderable makeCylinder(float radius, float height, Vector3 center, Material material) {
        float nextAngle;
        float angleIncrement;
        float uStep;
        ArrayList<Vertex> vertices;
        float f = radius;
        Vector3 vector3 = center;
        AndroidPreconditions.checkMinAndroidApiLevel();
        int numberOfSides = 24;
        float halfHeight = height / 2.0f;
        float angleIncrement2 = 0.2617994f;
        float uStep2 = 0.041666668f;
        ArrayList<Vertex> vertices2 = new ArrayList(98);
        ArrayList<Vertex> capVertices = new ArrayList(48);
        Vector3[] sidePositions = new Vector3[4];
        float nextAngle2 = 0.2617994f;
        float angle = 0.0f;
        int i = 0;
        while (true) {
            int i2 = 1;
            if (i >= 24) {
                break;
            }
            float nextAngle3 = nextAngle2;
            int numberOfSides2 = numberOfSides;
            ArrayList<Vertex> vertices3 = vertices2;
            ArrayList<Vertex> capVertices2 = capVertices;
            sidePositions[0] = new Vector3((float) (((double) f) * Math.cos((double) angle)), -halfHeight, (float) (((double) f) * Math.sin((double) angle)));
            nextAngle = nextAngle3;
            angleIncrement = angleIncrement2;
            uStep = uStep2;
            sidePositions[1] = new Vector3((float) (((double) f) * Math.cos((double) nextAngle)), -halfHeight, (float) (((double) f) * Math.sin((double) nextAngle)));
            sidePositions[2] = new Vector3((float) (((double) f) * Math.cos((double) angle)), halfHeight, (float) (((double) f) * Math.sin((double) angle)));
            sidePositions[3] = new Vector3((float) (((double) f) * Math.cos((double) nextAngle)), halfHeight, (float) (((double) f) * Math.sin((double) nextAngle)));
            numberOfSides = 0;
            while (numberOfSides < sidePositions.length) {
                Vector3 position = sidePositions[numberOfSides];
                Vector3 normal = new Vector3(position.f120x, 0.0f, position.f122z).normalized();
                position = Vector3.add(position, vector3);
                vertices = vertices3;
                vertices.add(Vertex.builder().setPosition(position).setNormal(normal).setUvCoordinate(new UvCoordinate((numberOfSides % 2 == 0 ? (float) i : (float) (i + 1)) * uStep, numberOfSides < 2 ? 0.0f : 1.0f)).build());
                capVertices2.add(Vertex.builder().setPosition(position).setNormal(numberOfSides > i2 ? Vector3.up() : Vector3.down()).setUvCoordinate(new UvCoordinate((position.f120x * (0.5f / f)) + 0.5f, (position.f122z * (0.5f / f)) + 0.5f)).build());
                numberOfSides++;
                vertices3 = vertices;
                i2 = 1;
            }
            angle += 0.2617994f;
            i++;
            vertices2 = vertices3;
            capVertices = capVertices2;
            angleIncrement2 = angleIncrement;
            uStep2 = uStep;
            int nextAngle4 = 1048971922 + nextAngle;
            numberOfSides = numberOfSides2;
        }
        angleIncrement = angleIncrement2;
        uStep = uStep2;
        vertices = vertices2;
        nextAngle = nextAngle2;
        ArrayList<Vertex> capVertices3 = capVertices;
        numberOfSides = vertices.size();
        vertices.add(Vertex.builder().setPosition(Vector3.add(vector3, new Vector3(0.0f, -halfHeight, 0.0f))).setNormal(Vector3.down()).setUvCoordinate(new UvCoordinate(0.5f, 0.5f)).build());
        vertices.addAll(capVertices3);
        int topCapPositionInVertices = vertices.size();
        vertices.add(Vertex.builder().setPosition(Vector3.add(vector3, new Vector3(0.0f, halfHeight, 0.0f))).setNormal(Vector3.up()).setUvCoordinate(new UvCoordinate(0.5f, 0.5f)).build());
        ArrayList<Integer> triangleIndices = new ArrayList(144);
        i = 0;
        while (i < 24) {
            triangleIndices.add(Integer.valueOf((i * 4) + 3));
            triangleIndices.add(Integer.valueOf((i * 4) + 1));
            triangleIndices.add(Integer.valueOf((i * 4) + 0));
            triangleIndices.add(Integer.valueOf((i * 4) + 0));
            triangleIndices.add(Integer.valueOf((i * 4) + 2));
            triangleIndices.add(Integer.valueOf((i * 4) + 3));
            i++;
            f = radius;
        }
        for (i = numberOfSides + 1; i < topCapPositionInVertices; i += 4) {
            triangleIndices.add(Integer.valueOf(numberOfSides));
            triangleIndices.add(Integer.valueOf(i));
            triangleIndices.add(Integer.valueOf(i + 1));
            triangleIndices.add(Integer.valueOf(topCapPositionInVertices));
            triangleIndices.add(Integer.valueOf(i + 3));
            triangleIndices.add(Integer.valueOf(i + 2));
        }
        Submesh submesh = Submesh.builder().setTriangleIndices(triangleIndices).setMaterial(material).build();
        RenderableDefinition renderableDefinition = RenderableDefinition.builder().setVertices(vertices).setSubmeshes(Arrays.asList(new Submesh[]{submesh})).build();
        try {
            ModelRenderable result = (ModelRenderable) ((ModelRenderable.Builder) ModelRenderable.builder().setSource(renderableDefinition)).build().get();
            if (result != null) {
                return result;
            }
            throw new AssertionError("Error creating renderable.");
        } catch (Exception ex) {
            RenderableDefinition renderableDefinition2 = renderableDefinition;
            throw new AssertionError("Error creating renderable.", ex);
        }
    }
}
