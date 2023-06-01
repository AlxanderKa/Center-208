package work;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Point {
    private double t, B, L, H, Vx, Vy, Vz;

    public Point(double t, double B, double L, double H, double Vx, double Vy, double Vz){
        this.t=t;
        this.B=B;
        this.L=L;
        this.H=H;
        this.Vx=Vx;
        this.Vy=Vy;
        this.Vz=Vz;
    }
    public double getSpeed (Point p){
        double speed = Math.sqrt(p.getVx()*p.getVx()*+p.getVy()*p.getVy()+p.getVz()*p.getVz());
        return speed;
    }


}
