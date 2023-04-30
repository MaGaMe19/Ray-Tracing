package com.raytracing;

import java.lang.Math;

public class Vec3 {
    public double[] e = new double[3];

    public Vec3(double e0, double e1, double e2) {
        this.e[0] = e0;
        this.e[1] = e1;
        this.e[2] = e2;
    }

    public double x() {
        return this.e[0];
    }

    public double y() {
        return this.e[1];
    }

    public double z() {
        return this.e[2];
    }

    public Vec3 negate() {
        return new Vec3(-this.e[0], -this.e[1], -this.e[2]);
    }

    private double getComp(int comp) {
        return this.e[comp];
    }

    private void setComp(int comp, double val) {
        this.e[comp] = val;
    }

    public double length() {
        return Math.sqrt(this.lengthSquared());
    }

    public double lengthSquared() {
        return this.e[0] * this.e[0] + this.e[1] * this.e[1] + this.e[2] * this.e[2];
    }

    public String toString() {
        return this.e[0] + " " + this.e[1] + " " + this.e[2];
    }

    public Color toColor() {
        return new Color(this.x(), this.y(), this.z());
    }

    public Point3 toPoint3() {
        return new Point3(this.x(), this.y(), this.z());
    }

    // Utility
    public static Vec3 add(Vec3 u, Vec3 v) {
        return new Vec3(u.e[0] + v.e[0], u.e[1] + v.e[1], u.e[2] + v.e[2]);
    }

    public static Vec3 sub(Vec3 u, Vec3 v) {
        return new Vec3(u.e[0] - v.e[0], u.e[1] - v.e[1], u.e[2] - v.e[2]);
    }

    public static Vec3 mul(Vec3 u, Vec3 v) {
        return new Vec3(u.e[0] * v.e[0], u.e[1] * v.e[1], u.e[2] * v.e[2]);
    }
    public static Vec3 mul(Vec3 v, double t) {
        return new Vec3(v.e[0] * t, v.e[1] * t, v.e[2] * t);
    }

    public static Vec3 div(Vec3 v, double t) {
        return mul(v, 1 / t);
    }

    public static double dot(Vec3 u, Vec3 v) {
        return u.e[0] * v.e[0] + u.e[1] * v.e[1] + u.e[2] * v.e[2];
    }

    public static Vec3 cross(Vec3 u, Vec3 v) {
        return new Vec3(
            u.e[1] * v.e[2] - u.e[2] * v.e[1],
            u.e[2] * v.e[0] - u.e[0] * v.e[2],
            u.e[0] * v.e[1] - u.e[1] * v.e[0]
        );
    }

    public static Vec3 unitVector(Vec3 v) {
        return div(v, v.length());
    }

    public static Vec3 random() {
        return new Vec3(Utility.randomFloat(), Utility.randomFloat(), Utility.randomFloat());
    }

    public static Vec3 random(double min, double max) {
        return new Vec3(
                Utility.randomFloat(min, max),
                Utility.randomFloat(min, max),
                Utility.randomFloat(min, max)
        );
    }

    public static Vec3 randomInUnitSphere() {
        while (true) {
            Vec3 p = random(-1, 1);
            if (p.lengthSquared() >= 1) {
                continue;
            }
            return p;
        }
    }
}

// Type aliases
class Point3 extends Vec3 {
    public Point3(double x, double y, double z) {
        super(x, y, z);
    }
}

class Color extends Vec3 {
    public Color(double r, double g, double b) {
        super(r, g, b);
    }
}

//