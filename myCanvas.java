/*
    @author Jianyuan Zhong
    @author Gao Fan
    At University of Rochester 17 Fall.
 */

import javax.swing.*;
import java.awt.*;

public class myCanvas extends JComponent{
    public Graph graph;
    private int scaleX;
    private int scaleY;



    public myCanvas(Graph graph){
        this.graph=graph;
    }

    public void drawThickLine(Graphics g, int x1, int y1, int x2, int y2, int thickness, Color c){
        //http://www.rgagnon.com/javadetails/java-0260.html
        // The thick line is in fact a filled polygon
        g.setColor(c);
        int dX = x2 - x1;
        int dY = y2 - y1;
        // line length
        double lineLength = Math.sqrt(dX * dX + dY * dY);

        double scale = (double)(thickness) / (2 * lineLength);

        // The x,y increments from an endpoint needed to create a rectangle...
        double ddx = -scale * (double)dY;
        double ddy = scale * (double)dX;
        ddx += (ddx > 0) ? 0.5 : -0.5;
        ddy += (ddy > 0) ? 0.5 : -0.5;
        int dx = (int)ddx;
        int dy = (int)ddy;

        // Now we can compute the corner points...
        int xPoints[] = new int[4];
        int yPoints[] = new int[4];

        xPoints[0] = x1 + dx; yPoints[0] = y1 + dy;
        xPoints[1] = x1 - dx; yPoints[1] = y1 - dy;
        xPoints[2] = x2 - dx; yPoints[2] = y2 - dy;
        xPoints[3] = x2 + dx; yPoints[3] = y2 + dy;

        g.fillPolygon(xPoints, yPoints, 4);
    }

    @Override
    protected void paintComponent(Graphics g){
        Double sx=(this.getWidth())/(graph.maxLong-graph.minLong);
        scaleX=sx.intValue();
        Double sy=(this.getHeight())/(graph.maxLat-graph.minLat);
        scaleY=sy.intValue();

        drawThickLine(g,0,0,getWidth(),0,3,Color.orange);
        drawThickLine(g,0,0,0,getHeight(),3,Color.orange);
        drawThickLine(g,0,getHeight(),getWidth(),getHeight(),3,Color.orange);
        drawThickLine(g,getWidth(),0,getWidth(),getHeight(),3,Color.orange);

        for(Edge e : graph.Edges){

            if(e.u.isPath && e.v.isPath)
                g.setColor(Color.RED);
            else
                g.setColor(Color.BLACK);

//            if(e.u.visited && e.v.visited)
//                g.setColor(Color.ORANGE);

            Double uy=getHeight()-(e.u.Latitude-graph.minLat)*scaleY;
            Double ux=(e.u.Longitude -graph.minLong)*scaleX;
            Double vy=getHeight()-(e.v.Latitude-graph.minLat)*scaleY;
            Double vx=(e.v.Longitude -graph.minLong)*scaleX;



            if(e.u.isPath && e.v.isPath){
                drawThickLine(g, ux.intValue(), uy.intValue(), vx.intValue(), vy.intValue(), 5, Color.RED);
            }
            else{
                g.drawLine(ux.intValue(),uy.intValue(),vx.intValue(),vy.intValue());
            }


            if(e.u.isStart) {
                g.setColor(Color.ORANGE);
                g.fillOval(ux.intValue()-5,uy.intValue()-7,14,14);
            }
            if(e.v.isStart) {
                g.setColor(Color.ORANGE);
                g.fillOval(vx.intValue()-5,vy.intValue()-7,14,14);
            }
            if(e.u.isEnd) {
                g.setColor(Color.BLUE);
                g.fillOval(ux.intValue()-5,uy.intValue()-7,14,14);
            }
            if(e.v.isEnd) {
                g.setColor(Color.BLUE);
                g.fillOval(vx.intValue()-5,vy.intValue()-7,14,14);
            }
        }
    }

    public void drawStart(String id){
        Vertex Start=graph.AdjacentList.get(id);
        Double uy=getHeight()-(Start.Latitude-graph.minLat)*scaleY;
        Double ux=(Start.Longitude -graph.minLong)*scaleX;
        Graphics g=this.getGraphics();
        g.setColor(Color.ORANGE);
        g.fillOval(ux.intValue()-5,uy.intValue()-7,14,14);
    }

    public void drawEnd(String id){
        Vertex end=graph.AdjacentList.get(id);
        Double uy=getHeight()-(end.Latitude-graph.minLat)*scaleY;
        Double ux=(end.Longitude -graph.minLong)*scaleX;
        Graphics g= this.getGraphics();
        g.setColor(Color.BLUE);
        g.fillOval(ux.intValue()-5,uy.intValue()-7,14,14);
    }

    @Override
    public Dimension getPreferredSize(){
//        return new Dimension(500,500);

        int height=1000;
        Double w=(((graph.maxLat-graph.minLat)/((graph.maxLong-graph.minLong)/2))*height);

        int width=w.intValue();

        return new Dimension(width,height);
    }
}
