import sys
from vec3 import *
from color import *
from ray import *
try:
    from tqdm import tqdm
except ModuleNotFoundError:
    print("This script requires tqdm to be installed.\n Install it using 'pip install tqdm'.")
    exit(-1)

# method to test wether a ray intersects a spehere using a quadratic equation
def hitSphere(center: Point3, radius, r: Ray) -> float:
    # substituting b with 2h
    oc = vecSub(r.origin(), center)
    a = r.direction().length_squared()
    h = dot(oc, r.direction())
    c = oc.length_squared() - radius**2
    discriminant = h**2 - a*c # value under sqrt, if > 0 => ray intersects sphere
    if discriminant < 0:
        return -1
    else:
        # return value which is needed to calculate the hit point on the sphere
        # -> t in ray equation
        return (-h - math.sqrt(discriminant)) / a

# returns a lerp between light purple and white as a background
def rayColor(r: Ray) -> Color:
    # t value passed from hitSphere used for calculating the hitpoint along the ray
    # when passing through a small sphere at (0, 0, -1)
    t = hitSphere(Point3(0, 0, -1), 0.5, r)
    if t > 0:
        # calculate surface normal at hitpoint (dist between hitpoint and sphere 
        # center) color the normals based on their vectors
        N = unit_vector(vecSub(r.at(t), Point3(0, 0, -1)))
        return vecScalarMul(Color(N.x() + 1, N.y() + 1, N.z() + 1), .5)

    # if ray doesn't intersect sphere color the sky normally
    unitDirection = unit_vector(r.direction())
    t = 0.5 * (unitDirection.y() + 1)
    # return (1 - t) * Color(1, 1, 1) + t * Color(0.5, 0.7, 1.0)
    return vecAdd(
            vecScalarMul(Color(1, 1, 1), (1 - t)), 
            vecScalarMul(Color(.839, .655, .98), t)
        )

# Image
ASPECT_RATIO = 16 / 9
IMAGE_WIDTH = 400
IMAGE_HEIGHT = int(IMAGE_WIDTH / ASPECT_RATIO)

# Camera
viewportHeight = 2
viewportWidth = ASPECT_RATIO * viewportHeight
focalLength = 1

origin = Point3(0, 0, 0)
horizontal = Vec3(viewportWidth, 0, 0)
vertical = Vec3(0, viewportHeight, 0)
# lower_left_corner = origin - horizontal/2 - vertical/2 - vec3(0, 0, focal_length)

lowerLeftCorner = vecSub(
        vecSub(
            vecSub(
                origin, vecScalarDiv(horizontal, 2)
            ), 
            vecScalarDiv(vertical, 2)
        ), 
        Vec3(0, 0, focalLength)
    )

# Render to ppm image format
with open("image.ppm", "w") as f:
    output = ""
    output += f"P3\n{IMAGE_WIDTH} {IMAGE_HEIGHT}\n255\n" # header

    # progress bar
    progress = tqdm(range(IMAGE_HEIGHT), "Generating Lines", unit=" lines")

    # values are written from top left to bottom right
    for j in range(IMAGE_HEIGHT-1, -1, -1):        
        for i in range(IMAGE_WIDTH):
            u = i / (IMAGE_WIDTH - 1)
            v = j / (IMAGE_HEIGHT - 1)

            # r = Ray(origin, lower_left_corner + u*horizontal + v*vertical - origin)
            r = Ray(origin, vecAdd(
                vecAdd(lowerLeftCorner, vecScalarMul(horizontal, u)),
                vecSub(vecScalarMul(vertical, v), origin)
            ))
            pixelColor = rayColor(r)
            # create color vector
            output += writeColor(pixelColor)
        progress.update(IMAGE_WIDTH - i)
    f.write(output)
 