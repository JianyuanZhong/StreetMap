/*
    @author Jianyuan Zhong
    @author Gao Fan
    At Univeristy of Rochester 2017 Fall
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class StreetMap {
    static String from = "";
    static String to = "";

    public static void navigate(Graph graph, myCanvas canvas, String a, String b){
        from = a;
        to = b;

        try{
            System.out.print("\nOrder of traveled intersection: \n");
            Double dis=0.0;

            List<Vertex> dir = graph.directions(from, to);
            if (!dir.isEmpty()){
                for (Vertex v : dir){
                    System.out.println(v.id + "\tLongitude:"+v.Longitude +"\tLatitude:"+v.Latitude+"\n\tMiles Traveled: "+v.dis);
                    dis=v.dis;
                }
                System.out.print("\nTotal Miles Traveled: "+dis+" miles\n");
            }
            else{
                System.out.print("No such path. \n");
            }

            canvas.repaint();

        }catch (NullPointerException e){
            System.out.print("No such place. \n");
        }
    }

    public static void main(String[] args){

        Graph graph=new Graph(args[0]);
        myCanvas canvas=new myCanvas(graph);

        if(args[1].equals("show")){
            if(args.length>3){
                if(args[2].equals("directions")) {

                    navigate(graph, canvas, args[3], args[4]);
                }
                else{
                    System.out.print("Please enter a valid command. \n");
                }
            }

            JFrame frame=new JFrame();
            frame.setLayout(new BorderLayout());

            JPanel inter = new JPanel();
            frame.setLayout(new BorderLayout());


            JLabel startL = new JLabel("From: ");
            JLabel endL = new JLabel("To: ");
            JTextField startT = new JTextField(from,5);
            startT.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    from = startT.getText();
                    System.out.print("from intersection: "+from+"\n");
                }
            });
            JTextField endT = new JTextField(to,5);
            endT.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    to = endT.getText();
                    System.out.print("to intersection: "+to+"\n");
                }
            });
            JButton button = new JButton("Go");
            button.setPreferredSize(new Dimension(80, 25));
            button.setBackground(new Color(209, 212, 206));
            button.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {

                    navigate(graph, canvas, startT.getText(), endT.getText());
                }
            });


            JButton startAt = new JButton("Show");
            startAt.setPreferredSize(new Dimension(80, 25));
            startAt.setBackground(new Color(209, 212, 206));
            startAt.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {

                    canvas.drawStart(startT.getText());
                }
            });

            JButton endAt = new JButton("Show");
            endAt.setPreferredSize(new Dimension(80, 25));
            endAt.setBackground(new Color(209, 212, 206));
            endAt.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {

                    canvas.drawEnd(endT.getText());
                }
            });


            inter.setLayout(new GridLayout(1,7));
            inter.add(startL);
            inter.add(startT);
            inter.add(startAt);
            inter.add(endL);
            inter.add(endT);
            inter.add(endAt);
            inter.add(button);

            frame.setLayout(new BorderLayout());
            frame.add(inter, BorderLayout.NORTH);
            frame.add(canvas, BorderLayout.CENTER);

            frame.pack();
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);
        }

        else if(args[1].equals("directions")) {
            System.out.print("\nOrder of traveled intersection: \n");
            for (Vertex v : graph.directions(args[2], args[3])) {
                System.out.println(v.id + " "+v.Longitude +" "+v.Latitude);
            }
            System.out.print("Miles traveled: "+graph.AdjacentList.get(args[3]).dis+"mils\n");
        }

        else{
            System.out.print("Please enter a valid command. \n");
        }
    }
}
