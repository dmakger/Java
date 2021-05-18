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

    JFrame theFrame;  // Сам экран
    JPanel mainPanel;  // Панель с флажками (checkbox)
    ArrayList<JCheckBox> checkboxList;  // Список с флажками (checkbox)

    Sequencer sequencer;
    Sequence sequence;
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
        new BeatBox().startUp();
    }

    public void startUp() {
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

    public MidiEvent makeEvent(int command, int channel, int one, int two, int tick) {
        MidiEvent event = null;
        try {
            ShortMessage a = new ShortMessage();
            a.setMessage(command, channel, one, two);
            event = new MidiEvent(a, tick);
        } catch (Exception ex) { }
        return event;
    }

}
