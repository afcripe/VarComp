package net.dahliasolutions.varcomp.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import net.dahliasolutions.varcomp.connectors.PositionsConnector;

public class Position {

    private final SimpleIntegerProperty position_id;
    private final SimpleStringProperty position_name;
    private final SimpleStringProperty position_description;
    private final SimpleIntegerProperty position_shares;

    public Position() {
        this.position_id = new SimpleIntegerProperty(0);
        this.position_name = new SimpleStringProperty("");
        this.position_description = new SimpleStringProperty("");
        this.position_shares = new SimpleIntegerProperty(0);
    }
    public Position(Integer positionID, String positionName, String PositionDescription, Integer positionShares) {
        this.position_id = new SimpleIntegerProperty(positionID);
        this.position_name = new SimpleStringProperty(positionName);
        this.position_description = new SimpleStringProperty(PositionDescription);
        this.position_shares = new SimpleIntegerProperty(positionShares);
    }

    public void setPosition(Position position) {
        setPosition_id(position.getPosition_id());
        setPosition(position.getPosition());
        setPosition_description(position.getPosition_description());
        setPosition_shares(position.getPosition_shares());
    }

    public Position getPosition() { return this; }

    public SimpleIntegerProperty position_idProperty() {return position_id;}

    public int getPosition_id() {return position_id.get();}

    public void setPosition_id(int position_id) {this.position_id.set(position_id);}

    public String getPosition_name() {return position_name.get();}

    public SimpleStringProperty position_nameProperty() {return position_name;}

    public void setPosition_name(String position_name) {this.position_name.set(position_name);}

    public String getPosition_description() {return position_description.get();}

    public SimpleStringProperty position_descriptionProperty() {return position_description;}

    public void setPosition_description(String position_description) {this.position_description.set(position_description);}

    public int getPosition_shares() {return position_shares.get();}

    public SimpleIntegerProperty position_sharesProperty() {return position_shares;}

    public void setPosition_shares(int position_shares) {this.position_shares.set(position_shares);}

    public Integer insertPosition() {return PositionsConnector.insertPosition(this).getPosition_id();}

    public void updatePosition() { PositionsConnector.updatePosition(this);}
}
