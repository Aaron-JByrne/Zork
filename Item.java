import java.io.Serializable;

abstract public class Item implements Nameable, Serializable {
    protected String description;
    protected String name;
    protected boolean isVisible;


    public Item(String name, String description) {
        this.name = name;
        this.description = description;
        this.isVisible = true;
    }

    abstract public String getDescription();

    abstract public void setDescription(String description);

    abstract public String getName();

    abstract public void setName(String name);

    abstract public void use();

}
