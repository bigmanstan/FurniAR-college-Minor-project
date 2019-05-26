package android.support.v7.app;

class TwilightCalculator {
    private static final float ALTIDUTE_CORRECTION_CIVIL_TWILIGHT = -0.10471976f;
    private static final float C1 = 0.0334196f;
    private static final float C2 = 3.49066E-4f;
    private static final float C3 = 5.236E-6f;
    public static final int DAY = 0;
    private static final float DEGREES_TO_RADIANS = 0.017453292f;
    private static final float J0 = 9.0E-4f;
    public static final int NIGHT = 1;
    private static final float OBLIQUITY = 0.4092797f;
    private static final long UTC_2000 = 946728000000L;
    private static TwilightCalculator sInstance;
    public int state;
    public long sunrise;
    public long sunset;

    TwilightCalculator() {
    }

    static TwilightCalculator getInstance() {
        if (sInstance == null) {
            sInstance = new TwilightCalculator();
        }
        return sInstance;
    }

    public void calculateTwilight(long time, double latitude, double longitude) {
        TwilightCalculator twilightCalculator = this;
        float daysSince2000 = ((float) (time - UTC_2000)) / 8.64E7f;
        float meanAnomaly = (0.01720197f * daysSince2000) + 6.24006f;
        double trueAnomaly = ((((double) meanAnomaly) + (Math.sin((double) meanAnomaly) * 0.03341960161924362d)) + (Math.sin((double) (2.0f * meanAnomaly)) * 3.4906598739326E-4d)) + (Math.sin((double) (3.0f * meanAnomaly)) * 5.236000106378924E-6d);
        double solarLng = (1.796593063d + trueAnomaly) + 3.141592653589793d;
        double arcLongitude = (-longitude) / 360.0d;
        float n = (float) Math.round(((double) (daysSince2000 - J0)) - arcLongitude);
        double solarTransitJ2000 = ((((double) (J0 + n)) + arcLongitude) + (Math.sin((double) meanAnomaly) * 0.0053d)) + (Math.sin(2.0d * solarLng) * -0.0069d);
        trueAnomaly = Math.asin(Math.sin(solarLng) * Math.sin(0.4092797040939331d));
        double latRad = 0.01745329238474369d * latitude;
        double cosHourAngle = (Math.sin(-0.10471975803375244d) - (Math.sin(latRad) * Math.sin(trueAnomaly))) / (Math.cos(latRad) * Math.cos(trueAnomaly));
        if (cosHourAngle >= 1.0d) {
            twilightCalculator.state = 1;
            twilightCalculator.sunset = -1;
            twilightCalculator.sunrise = -1;
        } else if (cosHourAngle <= -1.0d) {
            twilightCalculator.state = 0;
            twilightCalculator.sunset = -1;
            twilightCalculator.sunrise = -1;
        } else {
            daysSince2000 = (float) (Math.acos(cosHourAngle) / 6.283185307179586d);
            twilightCalculator.sunset = Math.round((((double) daysSince2000) + solarTransitJ2000) * 8.64E7d) + UTC_2000;
            twilightCalculator.sunrise = Math.round((solarTransitJ2000 - ((double) daysSince2000)) * 8.64E7d) + UTC_2000;
            if (twilightCalculator.sunrise >= time || twilightCalculator.sunset <= time) {
                twilightCalculator.state = 1;
            } else {
                twilightCalculator.state = 0;
            }
        }
    }
}
