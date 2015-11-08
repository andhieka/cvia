package cvia.reader_writer;

import com.itextpdf.text.pdf.parser.Vector;

/**
 * Created by andhieka on 4/11/15.
 */
public class TextChunk implements Comparable<TextChunk> {
    final String text;
    final Vector startLocation;
    final Vector endLocation;
    final Vector orientationVector;
    final int orientationMagnitude;
    final int distPerpendicular;
    final float distParallelStart;
    final float distParallelEnd;
    final float charSpaceWidth;

    public TextChunk(String string, Vector startLocation, Vector endLocation, float charSpaceWidth) {
        this.text = string;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.charSpaceWidth = charSpaceWidth;
        this.orientationVector = endLocation.subtract(startLocation).normalize();
        this.orientationMagnitude = (int)(Math.atan2((double)this.orientationVector.get(1), (double)this.orientationVector.get(0)) * 1000.0D);
        Vector origin = new Vector(0.0F, 0.0F, 1.0F);
        this.distPerpendicular = (int)startLocation.subtract(origin).cross(this.orientationVector).get(2);
        this.distParallelStart = this.orientationVector.dot(startLocation);
        this.distParallelEnd = this.orientationVector.dot(endLocation);
    }

    private void printDiagnostics() {
        System.out.println("Text (@" + this.startLocation + " -> " + this.endLocation + "): " + this.text);
        System.out.println("orientationMagnitude: " + this.orientationMagnitude);
        System.out.println("distPerpendicular: " + this.distPerpendicular);
        System.out.println("distParallel: " + this.distParallelStart);
    }

    public boolean sameLine(TextChunk as) {
        return this.orientationMagnitude != as.orientationMagnitude?false:this.distPerpendicular == as.distPerpendicular;
    }

    public float distanceFromEndOf(TextChunk other) {
        float distance = this.distParallelStart - other.distParallelEnd;
        return distance;
    }

    public int compareTo(TextChunk rhs) {
        if(this == rhs) {
            return 0;
        } else {
            int rslt = compareInts(this.orientationMagnitude, rhs.orientationMagnitude);
            if(rslt != 0) {
                return rslt;
            } else {
                rslt = compareInts(this.distPerpendicular, rhs.distPerpendicular);
                if(rslt != 0) {
                    return rslt;
                } else {
                    rslt = this.distParallelStart < rhs.distParallelStart?-1:1;
                    return rslt;
                }
            }
        }
    }

    private static int compareInts(int int1, int int2) {
        return int1 == int2?0:(int1 < int2?-1:1);
    }
}
