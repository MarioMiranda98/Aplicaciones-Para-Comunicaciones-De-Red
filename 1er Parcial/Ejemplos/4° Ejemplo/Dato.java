import java.io.Serializable;

public class Dato implements Serializable {
    private static final long serialVersionUID = 1L;
    public Dato(int v1, float v2, String v3) {
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
    }

    public int getV1() { return this.v1; }
    public float getV2() { return this.v2; }
    public String getV3() { return this.v3; }

    private int v1;
    private float v2;
    private String v3;
}