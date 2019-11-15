package com.company.area;

public class Circle {
    double rad;
    final double PI;

    public Circle(double r){
        this.rad = r;
        this.PI =3.14;
    }

    public double getArea(){
        return ( ( rad * rad ) * PI);
    }
}
