package test;

interface WayToTarget {
    void byBus();
    void byWalk();
    String BUS_TYPE = "B1";

    default void byTaxi(){
        System.out.println("by taxi");
    }

}
