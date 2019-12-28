package system;

public class LeafStub extends Leaf {

    public LeafStub(String name, int size) throws OutOfSpaceException {
        super(name, size);
    }

    public void setParent(Tree t){
        this.parent = t;
    }

    public void setName (String name){
        this.name = name;
    }
}
