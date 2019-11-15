package com.company.length;

public class Circle {
    double rad;
    final double PI;

    public Circle(double r){
        this.rad = r;
        this.PI =3.14;
    }

    public double getPerimeter(){
        return ( ( rad * 2 ) * PI);
    }
}
