
package IOFile;


import controllers.Controller;
import main.Main;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.ArrayList;

public class SaverAndLoader{

    private GameData data;


    public GameData read(String path) throws IOException {
        FileInputStream fileInputStream=new FileInputStream(new File(path));
        XMLDecoder decoder=new XMLDecoder(fileInputStream);
        this.data=(GameData) decoder.readObject();
        decoder.close();
        fileInputStream.close();
        return this.data;
    }
    public void loadSavedGamesPaths() throws IOException {
        FileInputStream fileInputStream=new FileInputStream(new File("res/SavedData/SavedGames.xml"));
        XMLDecoder decoder=new XMLDecoder(fileInputStream);
     Main.savedGamesPaths= (ArrayList<String>) decoder.readObject();
     decoder.close();
     fileInputStream.close();
    }
    public void saveNewPath(String newFile) throws IOException {
        boolean flag=false;
        for (String file:Main.savedGamesPaths) {
            if(file.equalsIgnoreCase(newFile))
            {
                flag=true;
                break;
            }
        }
        if(!flag) {
            Main.savedGamesPaths.add(newFile);
            FileOutputStream fileOutputStream = new FileOutputStream(new File("res/SavedData/SavedGames.xml"));
            XMLEncoder encoder = new XMLEncoder(fileOutputStream);
            encoder.writeObject(Main.savedGamesPaths);
            encoder.close();
            fileOutputStream.close();
        }
    }


    public void write(GameData data, String path) throws IOException {
        saveNewPath(path);
        FileOutputStream fileOutputStream=new FileOutputStream(new File(path));
        XMLEncoder encoder=new XMLEncoder(fileOutputStream);
        encoder.writeObject(data);
        encoder.close();
        fileOutputStream.close();
    }
    public void saveGame(String fileName)
    {
        data =new GameData();
        data.setPlayer(Controller.getInstance().getPlayer());
        data.setGameObjects(Controller.getInstance().getGameObjects());
        data.setGameTime((int) Controller.getInstance().getGameTime());
        try {
            write(data,fileName+".xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadGame(String fileName)
    {
        data=new GameData();
        try {
            data=read(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Controller.getInstance().setPlayer(data.getPlayer());
        Controller.getInstance().getPlayer().pauseUnPause();
        Controller.getInstance().setGameObjects(data.getGameObjects());
        Controller.getInstance().setGameTime(data.getGameTime());
        Controller.getInstance().getPlayer().refreshInfo();
    }
}