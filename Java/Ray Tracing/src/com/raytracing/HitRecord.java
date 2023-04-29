package com.raytracing;

public class HitRecord {
    // HitRecord class: keeps track of elements that ray intersects with

    private Point3 p;
    private Vec3 normal;
    private double t;
    private boolean frontFace;


    public HitRecord() {
        this.p = null;
        this.normal = null;
        this.t = 0;
        this.frontFace = false;

    }

    public void setFaceNormal(Ray r, Vec3 outwardNormal) {
        this.frontFace = Vec3.dot(r.direction(), outwardNormal) < 0;
        this.normal = this.frontFace ? outwardNormal : outwardNormal.negate();
    }

    public void setP(Point3 p) {
        this.p = p;
    }
    public void setNormal(Vec3 normal) {
        this.normal = normal;
    }
    public void setT(double t) {
        this.t = t;
    }
    public void setFrontFace(boolean frontFace) {
        this.frontFace = frontFace;
    }

    public Point3 getP() {
        return p;
    }
    public Vec3 getNormal() {
        return normal;
    }
    public double getT() {
        return t;
    }
    public boolean isFrontFace() {
        return frontFace;
    }
}
