package utils;

import java.util.Collection;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
 
public class Fx3DGroup extends Group {

    PhongMaterial redMaterial, greenMaterial, blueMaterial;

    public Translate t  = new Translate();
    public Rotate rx = new Rotate();
    { rx.setAxis(Rotate.X_AXIS); }
    public Rotate ry = new Rotate();
    { ry.setAxis(Rotate.Y_AXIS); }
    public Rotate rz = new Rotate();
    { rz.setAxis(Rotate.Z_AXIS); }
    public Scale s = new Scale();

    public Affine affine = new Affine();

    
    public Fx3DGroup() {
        super();
        init();
    }
    
    public Fx3DGroup(Node... children) {
        super(children);
        init();
    }
    
    public Fx3DGroup(Collection<Node> children) {
        super(children);
        init();
    }
    
    private void init() {
        getTransforms().addAll(t, rz, ry, rx, s);
        if (redMaterial == null)
        {
            redMaterial = new PhongMaterial();
            redMaterial.setDiffuseColor(Color.RED);
            greenMaterial = new PhongMaterial();
            greenMaterial.setDiffuseColor(Color.GREEN); 
            blueMaterial = new PhongMaterial();
            blueMaterial.setDiffuseColor(Color.BLUE);
        }
    }
    

    public void useAffine()
    {
        getTransforms().setAll(affine);

    }
    
    public void set3DTranslate(double x, double y, double z) {
        t.setX(x);
        t.setY(y);
        t.setZ(z);
    }



    public void set3DRotate(double x, double y, double z) {
        rx.setAngle(x);
        ry.setAngle(y);
        rz.setAngle(z);
    }


    public void set3DScale(double scaleFactor) {
        s.setX(scaleFactor);
        s.setY(scaleFactor);
        s.setZ(scaleFactor);
    }

    public void set3DScale(double scaleFactorX, double scaleFactorY, double scaleFactorZ) {
        s.setX(scaleFactorX);
        s.setY(scaleFactorY);
        s.setZ(scaleFactorZ);
    }


    
    public void AttachAxis()
    {
    	Cylinder x = createLine(Point3D.ZERO, new Point3D(1,0,0));
    	x.setMaterial(redMaterial);
    	Cylinder y = createLine(Point3D.ZERO, new Point3D(0,1,0));
    	y.setMaterial(greenMaterial);
    	Cylinder z = createLine(Point3D.ZERO, new Point3D(0,0,1));
    	z.setMaterial(blueMaterial);
    	this.getChildren().addAll(x,y,z);
    }

    // From Rahel Lüthy : https://netzwerg.ch/blog/2015/03/22/javafx-3d-line/
    public Cylinder createLine(Point3D origin, Point3D target) {
        Point3D yAxis = new Point3D(0, 1, 0);
        Point3D diff = target.subtract(origin);
        double height = diff.magnitude();

        Point3D mid = target.midpoint(origin);
        Translate moveToMidpoint = new Translate(mid.getX(), mid.getY(), mid.getZ());

        Point3D axisOfRotation = diff.crossProduct(yAxis);
        double angle = Math.acos(diff.normalize().dotProduct(yAxis));
        Rotate rotateAroundCenter = new Rotate(-Math.toDegrees(angle), axisOfRotation);

        Cylinder line = new Cylinder(0.01f, height);

        line.getTransforms().addAll(moveToMidpoint, rotateAroundCenter);

        return line;
    }
    
    public static Affine lookAt(Point3D from, Point3D to, Point3D ydir) {
        Point3D zVec = to.subtract(from).normalize();
        Point3D xVec = zVec.normalize().crossProduct(ydir).normalize();
        Point3D yVec = xVec.crossProduct(zVec).normalize();
        return new Affine(xVec.getX(), yVec.getX(), zVec.getX(), from.getX(),
                          xVec.getY(), yVec.getY(), zVec.getY(), from.getY(),
                          xVec.getZ(), yVec.getZ(), zVec.getZ(), from.getZ());
    }
    
    public void reset() {
        t.setX(0.0);
        t.setY(0.0);
        t.setZ(0.0);
        rx.setAngle(0.0);
        ry.setAngle(0.0);
        rz.setAngle(0.0);
        s.setX(1.0);
        s.setY(1.0);
        s.setZ(1.0);
        
        affine.setMxx(1);
        affine.setMxy(0);
        affine.setMxz(0);
        
        affine.setMyx(0);
        affine.setMyy(1);
        affine.setMyz(0);
        
        affine.setMzx(0);
        affine.setMzy(0);
        affine.setMzz(1);
    }

    public void debug() {
        System.out.println("t = (" +
                           t.getX() + ", " +
                           t.getY() + ", " +
                           t.getZ() + ")  " +
                           "r = (" +
                           rx.getAngle() + ", " +
                           ry.getAngle() + ", " +
                           rz.getAngle() + ")  " +
                           "s = (" +
                           s.getX() + ", " +
                           s.getY() + ", " +
                           s.getZ() + ") " +
                           "affine = " + affine);        
;
    }
}
