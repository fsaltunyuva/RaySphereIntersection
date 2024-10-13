public class Vector3 {
    public float x, y, z;

    // Constructor
    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    // Vector subtraction
    public Vector3 subtract(Vector3 other) {
        return new Vector3(this.x - other.x, this.y - other.y, this.z - other.z);
    }

    // Vector dot product
    public float dot(Vector3 other) {
        return this.x * other.x + this.y * other.y + this.z * other.z;
    }

    // Vector normalization (returns a unit vector)
    public Vector3 normalize() {
        float length = (float) Math.sqrt(x * x + y * y + z * z);
        return new Vector3(this.x / length, this.y / length, this.z / length);
    }
}
