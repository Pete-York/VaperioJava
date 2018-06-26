package ntuple.params;

public class FloatParam extends Param {
    float[] a;

    public Param setArray(float[] a) {
        this.a = a;
        return this;
    }

    public Object getValue(int i) {
        return a[i];
    }
}
