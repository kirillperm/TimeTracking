package timetracking;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.Timer;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;

public class TimeTracingFrame extends JFrame
{
    int abc[] = {0x0419, 0x0426, 0x0423, 0x041a, 0x0415, 0x041d, 0x0413, 0x0428, 0x0429, 0x0417, 0x0425, 0x042a,
        0x0424, 0x042b, 0x0412, 0x0410, 0x041f, 0x0420, 0x041e, 0x041b, 0x0414, 0x0416, 0x042d, 
            0x042f, 0x0427, 0x0421, 0x041c, 0x0418, 0x0422, 0x042c, 0x0411, 0x042e};
    abcButton ttButton[] = new abcButton[33];
    JTextField ttNameOut;
    String OutName = "";
    String LastName = " ";
    boolean checkTimer;
    Timer tempTimer;

    public TimeTracingFrame()
    {
        super("Time Tracing");
        this.setResizable(false);
        this.getContentPane().setBackground(new Color(218,218,218));
        Container conteinerFrame = this.getContentPane();
        this.setLayout( new GridBagLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setUndecorated(true);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        JLabel logoPTPP = new JLabel(new ImageIcon(getClass().getResource("logoPTPP.png")));
        conteinerFrame.add(logoPTPP, new GridBagConstraints(0, 0, 12, 1, 0, 0, GridBagConstraints.CENTER,
            GridBagConstraints.CENTER, new Insets(0, 0, 0, 0), 0, 0));
        final JLabel labelOutLastName = new JLabel(LastName);
        conteinerFrame.add(labelOutLastName, new GridBagConstraints(0, 1, 12, 1, 0, 0, GridBagConstraints.CENTER,
            GridBagConstraints.CENTER, new Insets(0, 0, 0, 0), 0, 0));
        Font LabelFont  = new Font(Font.SERIF, Font.BOLD, 22);
        labelOutLastName.setFont(LabelFont);
        //___________________________Вывод Клавиатуры начало
        int ttKeyboardX = 0; //положение клавиатуры по оси X
        int ttKeyboardY = 2; //положение клавиатуры по оси Y
        int ttKeyX = 0;
        int ttKeyY = 1;
        int intTemp = 0;
        ttNameOut = new JTextField(OutName);
        Font ttFont  = new Font(Font.SERIF, Font.BOLD, 18);
        ttNameOut.setFont(ttFont);
        conteinerFrame.add(ttNameOut, new GridBagConstraints(ttKeyboardX, ttKeyboardY, 10, 1, 1, 1, GridBagConstraints.CENTER,
            GridBagConstraints.HORIZONTAL, new Insets(0, 5, 0, 15), 0, 25));
        for (int ttChar = 0; ttChar <= 31; ttChar++)
        {
            ttButton[intTemp] = new abcButton(abc[ttChar]);
            conteinerFrame.add(ttButton[intTemp], new GridBagConstraints(ttKeyX + ttKeyboardX, ttKeyY + ttKeyboardY, 1, 1, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, new Insets(0, 5, 5, 15), 25, 25));
            ttButton[intTemp].setFont(ttFont);
            ttKeyX++;
            if (ttKeyX > 11 && ttKeyY == 1 ) { ttKeyX = 0; ttKeyY++; }
            if (ttKeyX > 10 && ttKeyY == 2 ) { ttKeyX = 0; ttKeyY++; }
            if (ttKeyY > 2 & ttKeyX < 1) { ttKeyX = 1; }
            intTemp++;
        }
        ttButton[intTemp] = new abcButton(((int)' ') - 32);
            conteinerFrame.add(ttButton[intTemp], new GridBagConstraints(ttKeyboardX + 1, 4 + ttKeyboardY, 9, 1, 1, 1, GridBagConstraints.CENTER,
        GridBagConstraints.BOTH, new Insets(0, 5, 10, 15), 25, 25));
        ttButton[intTemp].setFont(ttFont);
        JButton buttonBackspace = new JButton((char)0x2190 + " Backspace");
        conteinerFrame.add(buttonBackspace, new GridBagConstraints(10, ttKeyboardY, 2, 1, 1, 1, GridBagConstraints.CENTER,
           GridBagConstraints.BOTH, new Insets(70, 5, 70, 15), 0, 0));
        buttonBackspace.setFont(ttFont);
        buttonBackspace.addActionListener(new enterBackspace());
        JButton buttonEnter = new JButton((char)0x21b2 + " Enter");
        conteinerFrame.add(buttonEnter, new GridBagConstraints(ttKeyX, 3 + ttKeyboardY, 2, 2, 1, 1, GridBagConstraints.CENTER,
           GridBagConstraints.BOTH, new Insets(15, 5, 10, 15), 18, 18));
        buttonEnter.setFont(ttFont);
        buttonEnter.addActionListener(new enterEnter());
        //___________________________Вывод Клавиатуры конец
        Date tempDate = new Date();
        SimpleDateFormat tempDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm aa");
        final JLabel labelDateTime = new JLabel(tempDateFormat.format(tempDate));
        conteinerFrame.add(labelDateTime, new GridBagConstraints(0, 5 + ttKeyboardY, 9, 1, 0, 0, GridBagConstraints.WEST,
            GridBagConstraints.CENTER, new Insets(0, 30, 10, 0), 0, 0));
        Font DateFont  = new Font(Font.SERIF, Font.BOLD, 17);
        labelDateTime.setFont(DateFont);
        
        Timer dateTime = new Timer(10000,new ActionListener()
        { 
            @Override
            public void actionPerformed(ActionEvent ev)
            {
                Date tempDate = new Date();
                SimpleDateFormat tempDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm aa");
                labelDateTime.setText(tempDateFormat.format(tempDate)); 
            } 
        }); 
        dateTime.start();
        checkTimer = false;
        Timer checkLabelOutLastName = new Timer(100,new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ev)
            {
                if ( !" ".equals(labelOutLastName.getText()) && checkTimer == false)
                {
                    checkTimer = true;
                    tempTimer = new Timer(2000, new ActionListener()
                    {
                        @Override
                        public void actionPerformed(ActionEvent ev)
                        {
                            LastName = " ";
                            labelOutLastName.setText(LastName);
                        }
                    });
                    tempTimer.start();
                } 
                if ( checkTimer == true && " ".equals(labelOutLastName.getText()))
                { 
                    tempTimer.stop();
                    checkTimer = false;
                }
            } 
        });
        checkLabelOutLastName.start();
        Timer lastName = new Timer(1000,new ActionListener()
        { 
            @Override
            public void actionPerformed(ActionEvent ev)
            {
                labelOutLastName.setText(LastName); 
            } 
        }); 
        lastName.start();
    }

    public static void main (String[] args)
    {
        if (!checkFileOrDir(new File(System.getProperty("user.dir") + "\\Time\\"), "dir")) {System.exit(0);}
        createDelayFile();
        TimeTracingFrame window = new TimeTracingFrame();
        window.setVisible(true);
    }

    class abcButton extends JButton
    {
        public abcButton(final int abcButtonName)
        {
            super("" + (char)abcButtonName);
            this.addActionListener(new enterChar(abcButtonName));
        }
    }

    class enterChar implements ActionListener
    {
        char tempChar;
        private enterChar(int abcButtonName)
        {
            tempChar = (char)(abcButtonName + 32);
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            OutName = OutName + tempChar;
            if (checkFileOrDir(new File (System.getProperty("user.dir") + "\\people.txt"), "file"))
            {
                String tempString = nameSearch(OutName);
                if ( !tempString.equals("error search") )
                {
                    OutName = tempString;
                }
            } else
            {
                {System.exit(0);}
            }
            ttNameOut.setText(outFinalName(OutName));
        }
    }

    class enterBackspace implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (OutName.length() > 0) 
            {
                OutName = OutName.substring(0, (OutName.length() - 1));
                ttNameOut.setText(outFinalName(OutName));
            }
        }
    }

    class enterEnter implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            Date dateTime = new Date();
            SimpleDateFormat fileName = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat curentTime = new SimpleDateFormat("hh:mm aa");

            if (OutName.length() < 3 ) { return;}
            String tempText;
            tempText = curentTime.format(dateTime) + " - " + outFinalName(OutName);
            File fileTimeTracing = new File (System.getProperty("user.dir") + "\\Time\\" + fileName.format(dateTime) + ".txt");
            if (!checkFileOrDir(fileTimeTracing, "file")) {System.exit(0);}
            try 
            { 
                try (PrintWriter fileWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(fileTimeTracing, true)))) {
                    fileWriter.println(tempText);
                }
                deleteName(OutName);
                LastName = outFinalName(OutName) + " зарегестрирован в " + curentTime.format(dateTime);
            } catch(IOException ex) 
            { 
                System.out.println("Ошибка записи файла");
                System.exit(0); 
            }
            OutName = "";
            ttNameOut.setText(OutName);
        }
    }

    String nameSearch(String name)
    {
        if ( name.length() < 2 )
        {
            return "error search";
        }
        File tempFilePeople = new File(System.getProperty("user.dir") + "\\people.txt");
        try 
        {
            BufferedReader readerBuf = new BufferedReader(
                new InputStreamReader(
                    new FileInputStream(tempFilePeople), "UTF-8"));
            String value = name;
            String tempString;
            String tempStringOut = name;
            int tempCounter = 0;
            while((( tempString = readerBuf.readLine()) != null) && (tempCounter < 2) )
            {
                if ( tempString.length() > value.length())
                {
                String searchString = tempString.substring(0, value.length());
                searchString = searchString.toLowerCase();
                value = value.toLowerCase();
                if (searchString.contains(value) == true)
                {
                    tempCounter++;
                    tempStringOut = tempString;
                }
                }
            }
            if (tempCounter == 1)
            {
                return tempStringOut;
            } else
            {
                return "error search";
            }
        } catch (FileNotFoundException ex) 
        {
            return "error search";
        } catch (IOException ex) {
            return "error search";
        }
    }

    static boolean checkFileOrDir(File checkFile, String fileType)
    {
        if ( checkFile.exists()
                || checkFile.canRead()
                    || checkFile.canWrite()
                        || ( (fileType.equals("dir") && checkFile.isDirectory()) 
                            || (fileType.equals("file") && checkFile.isFile()) ) )
        {
            return true;
        } else
        {
            try
            {
                if ( fileType.equals("dir") ) { checkFile.mkdir(); return true;}
                if ( fileType.equals("file") ) { checkFile.createNewFile(); return true;}
            } catch (Exception e)
            {
                System.out.println("Ошибка создания каталога или файла");
                return false;
            }
        }
        return false;
    }

    static void createDelayFile()
    {
        Date dateTime = new Date();
        SimpleDateFormat fileName = new SimpleDateFormat("dd-MM-yyyy");
        File FileInput = new File (System.getProperty("user.dir") + "\\Time\\" + fileName.format(dateTime) + "_delay.txt");
        if (!FileInput.exists())
        {
        File FileOut = new File (System.getProperty("user.dir") + "\\people.txt");
        if (!checkFileOrDir(FileOut, "file")) {System.exit(0); }
        if (!checkFileOrDir(FileInput, "file")) {System.exit(0); }
        try
        {
            PrintWriter writer;
            try (BufferedReader reader = new BufferedReader(new FileReader(FileOut))) {
                writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(FileInput, true), "UTF-8"));
                String currentLine;
                while((currentLine = reader.readLine()) != null)
                {
                    writer.println(currentLine);
                }
            }
            writer.close();
        } catch (IOException e)
        {
            System.out.println("Ошибка создания каталога или файла");
            System.exit(0);
        }
        }
    }
    
    static void deleteName(String name)
    {
        Date dateTime = new Date();
        SimpleDateFormat fileName = new SimpleDateFormat("dd-MM-yyyy");
        File FileOut = new File (System.getProperty("user.dir") + "\\Time\\" + fileName.format(dateTime) + "_delay.txt");
        File FileTemp = new File (System.getProperty("user.dir") + "\\Time\\temp.txt");
        if (!checkFileOrDir(FileOut, "file")) {System.exit(0); }
        if (!checkFileOrDir(FileTemp, "file")) {System.exit(0); }
        try
        {
            PrintWriter writer;
            try (BufferedReader reader = new BufferedReader(new FileReader(FileOut))) {
                writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(FileTemp, true), "UTF-8"));
                String currentLine;
                while((currentLine = reader.readLine()) != null)
                {
                    if (!currentLine.contains(name.substring(0,name.length()-3)))
                    {
                        writer.println(currentLine);
                    }
                }
            }
            writer.close();
            FileOut.delete();
            FileTemp.renameTo(FileOut);
        } catch (IOException e)
        {
            System.out.println("Ошибка создания каталога или файла");
            System.exit(0);
        }
    }

    String outFinalName(String s)
    {
        String out = "";
        char[] temp = s.toCharArray();
        for (int i = 0; i < temp.length; i++)
        {
            if ( ((int) temp[i] >= 0x0430) && ((int) temp[i] <= 0x044f) )
            {
                if (i == 0)
                {
                    temp[i] = (char) ((int) temp[i] - 32 );
                }
                if ( i > 0 )
                {
                    if (temp[i-1] == ' ')
                    {
                        temp[i] = (char) ((int) temp[i] - 32 );
                    }
                }
                out = out + temp[i];
            } else
            {
                out = out + temp[i];
            }
        }
        return out;
    }
}