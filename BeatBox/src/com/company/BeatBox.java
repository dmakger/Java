package com.company;

import java.awt.*;
import javax.swing.*;
import java.io.*;
import javax.sound.midi.*;
import java.util.*;
import java.awt.event.*;
import java.net.*;
import javax.swing.event.*;

public class BeatBox {

    JFrame theFrame;
    JPanel mainPanel;
    JList incomingList;
    JTextField userMessage;
    ArrayList<JCheckBox> checkboxList;
    int nextNum;
    Vector<String> listVector = new Vector<String>();
    String userName;
    ObjectOutputStream out;
    ObjectInputStream in;
    HashMap<String, boolean[]> otherSeqsMap = new HashMap<String, boolean[]>();

    Sequencer sequencer;
    Sequence sequence;
    Sequence mySequence = null;
    Track track;

    String[] instrumentNames = {
            "Bass Drum", "Closed HI-Hat", "Open Hi-Hat", "Acoustic Snare",
            "Crash Cymbal", "Hand Clap", "High Tom", "Hi Bongo",
            "Maracas", "Whistle", "Low Conga", "Cowbell",
            "Vibraslap", "Low-mid Tom", "High Agogo", "Open Hi Conga"
    };

    int[] instruments = {
            35, 42, 46, 38,
            49, 39, 50, 60,
            70, 72, 64, 56,
            58, 47, 67, 63
    };

    public static void main (String[] args) {
//        new BeatBox().startUp(args[0]);  // Пользовательский индентификатор (имя)
        // Пример: %java BeatBox theFlash
        new BeatBox().startUp("Bruh");
    }

    public void startUp(String name) {
        userName = name;
        // Открываем соединение с сервером
        try {
            Socket sock = new Socket("127.0.0.1", 4242);
            out = new ObjectOutputStream(sock.getOutputStream());
            in = new ObjectInputStream(sock.getInputStream());
            Thread remote = new Thread(new RemoteReader());
            remote.start();
        } catch (Exception ex) {
            System.out.println("Не удалось подключиться к серверу. Вашу мелодию никто не услышит(");
        }
        setUpMidi();
        buildGUI();
    }

    public void buildGUI() {
        theFrame = new JFrame("BeatBox");
        BorderLayout layout = new BorderLayout();
        JPanel background = new JPanel(layout);
        background.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        checkboxList = new ArrayList<JCheckBox>();

        // --{ Buttons }--
        Box buttonBox = new Box(BoxLayout.Y_AXIS);

        // Start
        JButton start = new JButton("Start");
        start.addActionListener(new MyStartListener());
        buttonBox.add(start);

        // Stop
        JButton stop = new JButton("Stop");
        stop.addActionListener(new MyStopListener());
        buttonBox.add(stop);

        // Tempo Up
        JButton upTempo = new JButton("Tempo Up");
        upTempo.addActionListener(new MyUpTempoListener());
        buttonBox.add(upTempo);

        // Tempo Down
        JButton downTempo = new JButton("Tempo Down");
        downTempo.addActionListener(new MyDownTempoListener());
        buttonBox.add(downTempo);

        // Send
        JButton send = new JButton("Send");
        send.addActionListener(new MySendListener());
        buttonBox.add(send);

        // User Message
        userMessage = new JTextField();
        buttonBox.add(userMessage);

        // &&&
        incomingList = new JList();
        incomingList.addListSelectionListener(new MyListSelectionListener());
        incomingList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane theList = new JScrollPane(incomingList);
        buttonBox.add(theList);
        incomingList.setListData(listVector);


        // --{ Имена инструментов }--
        Box nameBox = new Box(BoxLayout.Y_AXIS);
        for (int i = 0; i < 16; ++i) {
            nameBox.add(new Label(instrumentNames[i]));
        }

        // Добавляем на фон кнопки и имена инструментов
        background.add(BorderLayout.EAST, buttonBox);
        background.add(BorderLayout.WEST, nameBox);

        // --{ Расставляем биты (checkbox) }--
        theFrame.getContentPane().add(background);
        GridLayout grid = new GridLayout(16, 16);
        grid.setVgap(1);
        grid.setHgap(2);
        mainPanel = new JPanel(grid);
        background.add(BorderLayout.CENTER, mainPanel);

        // Заполняем поле grid чекбоксами
        for (int i = 0; i < 256; ++i) {
            JCheckBox c = new JCheckBox();
            c.setSelected(false);
            checkboxList.add(c);
            mainPanel.add(c);
        }

        theFrame.setBounds(50, 50, 300, 300);
        theFrame.pack();
        theFrame.setVisible(true);
    }

    /**
     * Создание трека
     * */
    public void setUpMidi() {
        try {
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequence = new Sequence(Sequence.PPQ, 4);
            track = sequence.createTrack();
            sequencer.setTempoInBPM(120);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Создание трека с учетом всех флажков (checkbox)
     * */
    public void buildTrackAndStart() {
        ArrayList<Integer> trackList = null; // Здесь храняться инструменты для каждого трека
        sequence.deleteTrack(track);
        track = sequence.createTrack();

        for (int i = 0; i < 16; ++i) {
            trackList = new ArrayList<Integer>();

            for (int j = 0; j < 16; ++j) {
                JCheckBox jc = (JCheckBox) checkboxList.get(j + (16 * i));
                if (jc.isSelected()) {
                    int key = instruments[i];
                    trackList.add(key);
                } else {
                    trackList.add(null);
                }
            }

            makeTracks(trackList);
        }
        track.add(makeEvent(192, 9, 1, 0, 15));
        try {
            sequencer.setSequence(sequence);
            sequencer.setLoopCount(sequencer.LOOP_CONTINUOUSLY);
            sequencer.start();
            sequencer.setTempoInBPM(120);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Слушатель для кнопки "Start". Запускает трек
     * */
    public class MyStartListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            buildTrackAndStart();
        }
    }

    /**
     * Слушатель для кнопки "Stop". Останавливает трек
     * */
    public class MyStopListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            sequencer.stop();
        }
    }

    /**
     * Слушатель для кнопки "Temp Up". Повышает темп
     * */
    public class MyUpTempoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            float tempoFactor = sequencer.getTempoFactor();
            sequencer.setTempoFactor((float) (tempoFactor * 1.03));
        }
    }

    /**
     * Слушатель для кнопки "Temp Down". Понижает темп
     * */
    public class MyDownTempoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            float tempoFactor = sequencer.getTempoFactor();
            sequencer.setTempoFactor((float) (tempoFactor * 0.97));
        }
    }

    /**
     * Слушатель для кнопки "Send". Отправляет сообщение
     * */
    public class MySendListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            boolean[] checkboxState = new boolean[256];  // Состояние всех флажков (чекбоксов)
            for (int i = 0; i < 256; ++i) {
                JCheckBox check = (JCheckBox) checkboxList.get(i);
                if (check.isSelected()) {
                    checkboxState[i] = true;
                }

                String messageToSend = null;
                try {
                    out.writeObject(userName + nextNum + ": " + userMessage.getText());
                    ++nextNum;
                    out.writeObject(checkboxState);
                } catch (Exception ex) {
                    System.out.println("Bruh( Письмо не будет отправленно, т.к. вы не подключились к серверу");
                }
                userMessage.setText("");
            }
        }
    }

    /**
     * Слушатель для кнопки ... Когда пользователь выбирает сообщение
     * из списка, проигрывает шаблон.
     * */
    public class MyListSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent le) {
            if (!le.getValueIsAdjusting()) {
                String selected = (String) incomingList.getSelectedValue();
                if (selected != null) {
                    boolean[] selectedState = (boolean[]) otherSeqsMap.get(selected);
                    changeSequence(selectedState);
                    sequencer.stop();
                    buildTrackAndStart();
                }
            }
        }
    }

    /**
     * Задача потока - читать данные, присылаемые сервером.
     * Под данными понимается два сериализованных объекта: строковое сообщение и
     * музыкальная последовательность.
     * */
    public class RemoteReader implements Runnable {
        boolean[] checkboxState = null;
        String nameToShow = null;
        Object obj = null;

        @Override
        public void run() {
            try {
                while ((obj=in.readObject()) != null) {
                    System.out.println("Объект для сервера: " + obj.getClass());
                    String nameToShow = (String) obj;
                    checkboxState = (boolean[]) in.readObject();
                    otherSeqsMap.put(nameToShow, checkboxState);
                    listVector.add(nameToShow);
                    incomingList.setListData(listVector);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public class MyPlayMineListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent a) {
            if (mySequence != null) {
                sequence = mySequence;
            }
        }
    }

    /**
     * Метод который вызывается, когда пользователь выбирает пункт из списка.
     * Сам метод устанавливает переданный шаблон
     * */
    public void changeSequence(boolean[] checkboxState) {
        for (int i = 0; i < 256; ++i) {
            JCheckBox check = (JCheckBox) checkboxList.get(i);
            check.setSelected(checkboxState[i]);
        }
    }

    public void makeTracks(ArrayList list) {
        Iterator it = list.iterator();
        for (int i = 0; i < 16; ++i) {
            Integer num = (Integer) it.next();
            if (num != null) {
                int numKey = num.intValue();
                track.add(makeEvent(144, 9, numKey, 100, i));
                track.add(makeEvent(128, 9, numKey, 100, i + 1));
            }
        }
    }

    public MidiEvent makeEvent(int comd, int chan, int one, int two, int tick) {
        MidiEvent event = null;
        try {
            ShortMessage a = new ShortMessage();
            a.setMessage(comd, chan, one, two);
            event = new MidiEvent(a, tick);
        } catch (Exception ex) { }
        return event;
    }

}
