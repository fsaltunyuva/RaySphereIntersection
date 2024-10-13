import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        // Image properties
        final int width = 256;
        final int height = 256;

        // Sphere properties
        Vector3 sphereCenter = new Vector3(0, 0, -1);  // Sphere center at z = -1 (in front of the camera)
        float radius = 0.5f;

        // Camera properties (ray origin at z = 0)
        Vector3 cameraOrigin = new Vector3(0, 0, 0);

        // Open a file in write mode
        FileWriter imageFile = new FileWriter("ray_sphere.ppm");

        // Write the PPM header (P3 means plain text format)
        imageFile.write("P3\n" + width + " " + height + "\n255\n");

        // Loop through each pixel and cast a ray
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                // Normalize coordinates to [-1, 1]
                float u = (x + 0.5f) / width * 2.0f - 1.0f;
                float v = (y + 0.5f) / height * 2.0f - 1.0f;

                // Define ray direction from the camera through the pixel
                Vector3 rayDir = new Vector3(u, v, -1).normalize();

                // Check for intersection with the sphere
                float[] t = new float[2];  // t is the distance to the intersection point

                if (rayIntersectsSphere(cameraOrigin, rayDir, sphereCenter, radius, t)) {
                    // The leaving point of the ray can be accessed by t[1]
                    imageFile.write("255 0 0\t"); // If the ray hits the sphere, color it red
                }
                else {
                    imageFile.write("255 255 255\t"); // Otherwise, color it white (background)
                }
            }
            imageFile.write("\n");  // New line after each row
        }

        // Close the file
        imageFile.close();

        System.out.println("Ray-traced sphere PPM image created successfully!");
    }

    // Ray-sphere intersection function
    public static boolean rayIntersectsSphere(Vector3 rayOrigin, Vector3 rayDir, Vector3 sphereCenter, float radius, float[] t) {
        Vector3 pc = rayOrigin.subtract(sphereCenter); // p - c

        float a = rayDir.dot(rayDir); // d . d
        float b = 2.0f * pc.dot(rayDir); // 2d . (p - c)
        float c = pc.dot(pc) - radius * radius; // (p - c) . (p - c) - r^2

        float discriminant = b * b - 4 * a * c; // b^2 - 4ac

        if (discriminant < 0) {
            return false;  // No intersection
        } else {
            t[0] = (-b - (float) Math.sqrt(discriminant)) / (2.0f * a); // Nearest intersection point
            t[1] = (-b + (float) Math.sqrt(discriminant)) / (2.0f * a); // Farthest intersection point
            return true;
        }
    }
}