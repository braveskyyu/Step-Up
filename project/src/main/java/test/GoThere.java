package test;

import java.io.File;

public class GoThere implements WayToTarget{
    private String standName;

    public GoThere() {
        this("test");
    }

    @Override
    public void byBus() {

    }

    public GoThere(String name){
        this.standName = name;
    }

    @Override
    public void byWalk() {
        this.byTaxi();

        System.out.println(BUS_TYPE);
    }

    @Override
    public void byTaxi() {
        WayToTarget.super.byTaxi();
        System.out.println("mytest");
    }

    public static void main(String[] args) {
        loopFolder("D:"+File.separator+"project"+File.separator+"AvMeT_V1.9"+File.separator+"AvMeT_V1.9");
    }

    private static void loopFolder(String path) {
        System.out.println("=================="+path+"========================");
        File file = new File(path);
        if (file  == null) return;

        for (File f:file.listFiles()) {
            //if (f  == null) continue;
            if (f.isDirectory()){
                loopFolder(f.getAbsolutePath());
            }
            else{
                System.out.println(f.getName()+";"+f.length()/1000+" KB");
            }

        }

    }

    class GoGo{
        static int sign = 1;

        public void showSign(){
            System.out.println("show sign");
        }
    }
}
