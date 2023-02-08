package pro;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Music_Player {

    static Node start;
    static class Node {
        String path;
        Node next;
        Node prev;
    };

//    LinkList Functions
    static void insertEnd(String value)
    {
        if (start == null) {
            Node new_node = new Node();
            new_node.path = value;
            new_node.next = new_node.prev = new_node;
            start = new_node;
            return;
        }
        Node last = (start).prev;
        Node new_node = new Node();
        new_node.path = value;
        new_node.next = start;
        (start).prev = new_node;
        new_node.prev = last;
        last.next = new_node;
    }

    // play next music
    static void next()
    {
        Node temp = start;
        while (true) {
            String newVar = temp.path;
            if(newVar.equals(Listening_Music)){
                break;
            }
            temp=temp.next;
        }
            Listening_Music = temp.next.path;
    }

    // play prev music
    static void prev()
    {
        Node temp = start;
        while (true) {
            String newVar = temp.path;
            if(newVar.equals(Listening_Music)){
                break;
            }
            temp=temp.next;
        }
            Listening_Music = temp.prev.path;
    }

    static void display()
    {
        Node temp = start;

        System.out.printf(
                "\nTraversal in forward direction \n");
        while (temp.next != start) {
            System.out.printf("%s ", temp.path);
            temp = temp.next;
        }
        System.out.printf("%s ", temp.path);
    }

//    **********   Members ***************
    static String Listening_Music="Baarish-Atif_Aslam.wav";
    static File musicPath = new File("music.wav");
    static long clipTimePosition;
    static boolean status=false;
    static int count = 0;

//    ***********  functions   ************

    // loading screen
public static void loader(){
    JFrame load=new JFrame();//creating instance of JFrame
    // load logo

    JLabel main_logo = new JLabel();
    main_logo.setIcon(new ImageIcon("src/Player/main-logo.png"));
    Dimension size0 = main_logo.getPreferredSize();
    main_logo.setBounds(100,200,size0.width,size0.height);

// progress bar
    JProgressBar jbl;
    int i=0,num=0;
    jbl=new JProgressBar(0,100);
    jbl.setBounds(50,620,450,20);
    jbl.setBackground(Color.gray);
    jbl.setForeground(Color.white);
    jbl.setValue(0);
//        adding Component
    load.add(main_logo);
    load.add(jbl);
    load.setSize(600,800);
    load.setLayout(null);//using no layout managers
    load.setLocationRelativeTo(null);
    load.setVisible(true);//making the frame visible

    while(i<100){
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        i=i+3;
        jbl.setValue(i);
    }
    load.setVisible(false);
    layout();
}

    // home page
public static void layout(){
//        linkList music = new linkList();
        insertEnd("Baarish-Atif_Aslam.wav");
        insertEnd("Noor_E_Azal-Atif_Aslam.wav");
        insertEnd("Yaad_Tehari_Atif_Aslam .wav");
        insertEnd("Pepsi_Battle_Bands-Atif_Aslam.wav");
        display();

        JFrame f=new JFrame();//creating instance of JFrame

        // progress bar
        JProgressBar jb;
        jb=new JProgressBar(0,100);
        jb.setBounds(50,560,450,30);
        jb.setValue(0);
        jb.setStringPainted(true);

//        current music
        JLabel current = new JLabel("CenterCenter",SwingConstants.CENTER);
        current.setBounds(0,00,600,50);
        current.setForeground(Color.white);
        current.setBackground(Color.gray);
        current.setOpaque(true);
        String music_name = "Musaafir aa sikha de tu muj ku";
        String Author_name = "Atif Aslam";
        current.setText("Current Playing : -");

//        music icon
        JLabel musicIcon = new JLabel();
        musicIcon.setIcon(new ImageIcon("src/music.png"));
        Dimension size = musicIcon.getPreferredSize();
        musicIcon.setBounds(25,100,size.width,size.height);

//        prev icon
        JButton prevIcon = new JButton();
        prevIcon.setContentAreaFilled(false);
        prevIcon.setBorder(null);
        prevIcon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jb.setValue(0);
                prev();
                current.setText("Current Music : "+Listening_Music+"\n CLick Play Button to play");
            }
        } );
        prevIcon.setIcon(new ImageIcon("src/Player/prev.png"));
        Dimension size1 = musicIcon.getPreferredSize();
        prevIcon.setBounds(50,650,100,100);
        prevIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                prevIcon.setBorder(BorderFactory.createLineBorder(Color.blue));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                prevIcon.setBorder(null);
            }
        });


//        pause/play icon
        JButton playIcon = new JButton();
        playIcon.setContentAreaFilled(false);
        playIcon.setBorder(null);
        ImageIcon play = new ImageIcon("src/Player/play.png");
        ImageIcon pause = new ImageIcon("src/Player/pause.png");
        playIcon.setIcon(play);
        Dimension size2 = musicIcon.getPreferredSize();
        playIcon.setBounds(220,650,100,100);
        playIcon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    //        music path
                try {
                    current.setText("Currently Playing : "+Listening_Music);
                    AudioInputStream Audio = null;
                    String temp = "src/audio/"+Listening_Music;
                    musicPath=new File(temp);
                    Audio = AudioSystem.getAudioInputStream(musicPath);
                    Clip clip = AudioSystem.getClip();
                    if (!status) {
                        clip.open(Audio);
                        playIcon.setIcon(pause);
                        status = false;
                        if(clipTimePosition>0){
                            clip.setMicrosecondPosition(clipTimePosition);
                        }
                        clip.start();
                        clip.loop(Clip.LOOP_CONTINUOUSLY);
                        JOptionPane.showMessageDialog(f, "Playing ...."+Listening_Music+"\nPlease Enter Okay to Pause the audio");

                        float curr = clip.getMicrosecondPosition()/1000000;
                        float total = clip.getMicrosecondLength()/1000000;
                        System.out.println(curr+" divide "+total);
                        float Percent = (curr/total)*100;
                        System.out.println(Percent);
                        jb.setValue((int) Percent);
                        playIcon.setIcon(play);
                        clipTimePosition = clip.getMicrosecondPosition();
                        clip.stop();
                    }
                }catch (Exception ex) {
                        ex.printStackTrace();}

                }
        } );
        playIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                playIcon.setBorder(BorderFactory.createLineBorder(Color.blue));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                playIcon.setBorder(null);
            }
        });


//        next icon
        JButton nextIcon = new JButton();
        nextIcon.setContentAreaFilled(false);
        nextIcon.setBorder(null);
        nextIcon.setIcon(new ImageIcon("src/Player/next.png"));
        Dimension size3 = musicIcon.getPreferredSize();
        nextIcon.setBounds(400,650,100,100);
        nextIcon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // music path
                jb.setValue(0);
                next();
                current.setText("Current Music : "+Listening_Music+"\n Click Play Button to continue");
            }
        } );
        nextIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                nextIcon.setBorder(BorderFactory.createLineBorder(Color.blue));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                nextIcon.setBorder(null);
            }
        });


        //        button
        JButton b1=new JButton("Dark");//creating instance of JButton
        b1.setBackground(Color.blue);
        b1.setForeground(Color.white);
        b1.setBounds(495,0,100, 50);
        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(b1.getText().equals("Dark")){
                b1.setText("Light");
                f.setForeground(Color.black);
                f.getContentPane().setBackground(Color.black);
                current.setBackground(Color.white);
                current.setForeground(Color.BLACK);
            } else{
                    b1.setText("Dark");
                    f.setForeground(Color.GRAY);
                    f.getContentPane().setBackground(Color.white);
                    current.setBackground(Color.gray);
                    current.setForeground(Color.white);
                }
            }
        } );

//        adding components
           f.add(b1);
        f.add(jb);
        f.add(current);
        f.add(playIcon);
        f.add(nextIcon);
        f.add(prevIcon);
        f.add(musicIcon);
        f.setLocationRelativeTo(null);
        f.setSize(600,800);//400 width and 500 height
        f.setLayout(null);//using no layout managers
        f.setLocationRelativeTo(null);
        f.setVisible(true);
}

    public static void main(String[] args){
        Music_Player.loader();

    }

}

