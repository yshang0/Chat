import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;


public class ChatClient extends Frame {
    Socket s = null;
    DataOutputStream dos;
    TextField tfTxt = new TextField();
    //A TextField object is a text component that allows for the editing of a single line of text.

    TextArea taContent = new TextArea();
    //A TextArea object is a multi-line region that displays text. It can be set to allow editing or to be read-only.

    public static void main(String[] args) {
        ChatClient cc = new ChatClient();
        cc.launchFrame();
    }

    public void launchFrame() {

        setLocation(400, 300);//setup location and size of window;
        this.setSize(300, 300);

        add(tfTxt, BorderLayout.SOUTH);
        add(taContent, BorderLayout.NORTH);//A border layout lays out a container, arranging and resizing its components to fit in five regions: north, south, east, west, and center.

        pack();//Causes this Window to be sized to fit the preferred size and layouts of its subcomponents.

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent arg0){
                System.exit(0);
            }
        });
        //closing the window;
        //WindowListener: The listener interface for receiving window events.
        //WindowAdapter: An abstract adapter class for receiving window events. The methods in this class are empty. This class exists as convenience for creating listener objects.

        tfTxt.addActionListener(new TfListener());

        setVisible(true);
        connect();
    }

    public void connect() {
        try {
            s = new Socket("127.0.0.1", 8888);
            dos = new DataOutputStream(s.getOutputStream());
            System.out.println("connected!");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {

        try {
            dos.close();
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private class TfListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String str = tfTxt.getText().trim(); // get string from tfTxt;
            taContent.setText(str); // put string into taContent;
            tfTxt.setText("");
            try {
                dos.writeUTF(str);//Writes a string to the underlying output stream
                dos.flush();//Flushes this data output stream;This forces any buffered output bytes to be written out to the stream.
                //dos.close();

            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
    //ActionListener: The listener interface for receiving action events.
}
