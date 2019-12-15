package application;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: Representation Tracker
// Files:BTree.java, BTreeADT.java, Congress.java, CongressTests.java,
// Legislator.java, Main.java
//
// Course: CS400, Fall 2019
//
// Author: Nate Goethel
// Email: ngoethel@wisc.edu
// Lecturer's Name: Andrew Kuemmel
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully
// acknowledge and credit those sources of help here. Instructors and TAs do
// not need to be credited here, but tutors, friends, relatives, room mates,
// strangers, and others do. If you received no outside help from either type
// of source, then please explicitly indicate NONE.
//
// Persons: NONE
//
// Online Sources: https://docs.oracle.com/javafx/2/charts/pie-chart.htm#CIHFDADD -
// helped with pie charts
// https://stackoverflow.com/questions/22166610/how-to-create-a-popup-windows-in-javafx -
// helped with popups
// https://stackoverflow.com/questions/47296215/set-labels-and-text-field-alignment-in-javafx
// https://stackoverflow.com/questions/22166610/how-to-create-a-popup-windows-in-javafx
// https://stackoverflow.com/questions/59339538/show-a-dialog-window-when-a-user-clicks-the-x-button
// https://examples.javacodegeeks.com/desktop-java/javafx/dialog-javafx/javafx-dialog-example/
// https://docs.oracle.com/javafx/2/charts/pie-chart.htm
// https://examples.javacodegeeks.com/desktop-java/javafx/listview-javafx/javafx-listview-example/
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////


public class Main extends Application {

  String partyValue = "";
  String stateValue = "";
  String bodyValue = "";
  String genderValue = "";

  Congress congress = new Congress();

  @Override
  public void start(Stage primaryStage) {
    try {



      /**
       * Create controls
       */

      // Data for percentage pie chart for senate gender
      ObservableList<PieChart.Data> senateGenderData = FXCollections.observableArrayList(
          new PieChart.Data("Female", (1.0 * congress.getNumFemSenate())),
          new PieChart.Data("Male", (1.0 * congress.getNumMaleSenate())));

      // Pie chart object for senate gender
      PieChart senateGender = new PieChart(senateGenderData);
      senateGender.setTitle("Senate Split by Gender");

      // Data for percentage pie chart for senate parties
      ObservableList<PieChart.Data> senatePartyData = FXCollections.observableArrayList(
          new PieChart.Data("Democrat", congress.getNumDemsSenate()),
          new PieChart.Data("Republican", congress.getNumRepsSenate()),
          new PieChart.Data("Independent", congress.getNumIndsSenate()));

      // Pie chart object for senate parties
      PieChart senateParties = new PieChart(senatePartyData);
      senateParties.setTitle("Senate Split by Party");

      // Data for percentage pie chart for house gender
      ObservableList<PieChart.Data> houseGenderData = FXCollections.observableArrayList(
          new PieChart.Data("Female", (1.0 * congress.getNumFemHouse())),
          new PieChart.Data("Male", (1.0 * congress.getNumMaleHouse())));

      // Pie chart object for house gender
      PieChart houseGender = new PieChart(houseGenderData);
      houseGender.setTitle("House Split by Gender");

      // Data for percentage pie chart for house parties
      ObservableList<PieChart.Data> housePartyData = FXCollections.observableArrayList(
          new PieChart.Data("Democrat", (1.0 * congress.getNumDemsHouse())),
          new PieChart.Data("Republican", (1.0 * congress.getNumRepsHouse())),
          new PieChart.Data("Independent", (1.0 * congress.getNumIndsHouse())));

      // Pie chart object for house party data
      PieChart houseParties = new PieChart(housePartyData);
      houseParties.setTitle("House Split by Party");
      // houseParties.setMaxSize(200, 200);


      // list of states for the State ComboBox
      ObservableList<String> states = FXCollections.observableArrayList("", "AL", "AK", "AZ", "AR",
          "CA", "CO", "CT", "DE", "FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME",
          "MD", "MA", "MI", "MN", "MS", "MO", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH",
          "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY");

      // two options for gender (male or female)
      ObservableList<String> gender = FXCollections.observableArrayList("", "Male", "Female");

      // options for legislative body
      ObservableList<String> body = FXCollections.observableArrayList("", "House", "Senate");

      // three options for party
      ObservableList<String> party =
          FXCollections.observableArrayList("", "Democrat", "Republican", "Independent");

      // create a ComboBox for the list of states
      ComboBox<String> stateBox = new ComboBox<String>(states);
      Label stateLabel = new Label("State:");

      // add a listener for when the user changes the State field
      stateBox.valueProperty().addListener(new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue,
            String newValue) {
          stateValue = newValue;
        }

      });

      // create a ComboBox for party
      ComboBox<String> partyBox = new ComboBox<String>(party);
      Label partyLabel = new Label("Party:");

      // add a listener for partyBox
      partyBox.valueProperty().addListener(new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue,
            String newValue) {
          partyValue = newValue;
        }
      });

      // create a ComboBox for gender
      ComboBox<String> genderBox = new ComboBox<String>(gender);
      Label genderLabel = new Label("Gender:");

      // add a listener for genderBox
      genderBox.valueProperty().addListener(new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue,
            String newValue) {

          if (newValue.equals("Male")) {
            genderValue = "M";
          } else if (newValue.equals("Female")) {
            genderValue = "F";
          } else {
            genderValue = newValue;
          }

        }
      });

      // create a ComboBox for legislative body
      ComboBox<String> bodyBox = new ComboBox<String>(body);
      Label bodyLabel = new Label("Body:");

      // create a listener for bodyBox
      bodyBox.valueProperty().addListener(new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue,
            String newValue) {
          bodyValue = newValue;
        }
      });

      // create a button for opening the Senate stats scene
      Button seeSenateStatsButton = new Button();
      seeSenateStatsButton.setText("Senate Stats NOT WORKING");

      // create a button for opening the House stats scene
      Button seeHouseStatsButton = new Button();
      seeHouseStatsButton.setText("House Stats NOT WORKING");

      // button for adding a legislator
      Button addLegislator = new Button("Add Legislator NOT WORKING");


      Button removeLegislator = new Button("Remove Legislator NOT WORKING");

      // create search button and tooltip
      Button searchButton = new Button("Search Congress");
      Tooltip searchTip = new Tooltip("Search Congress based on certain criteria");
      searchButton.setTooltip(searchTip);

      // create clear button
      Button clearButton = new Button("Clear");

      // create submit button
      Button submitButton = new Button("Submit");

      // create back button
      Button backButton = new Button("Back");

      // create save button
      Button saveButton = new Button();
      Tooltip saveTip = new Tooltip("Save search to CSV file");
      saveButton.setTooltip(saveTip);
      Image saveImage = new Image(this.getClass().getResourceAsStream("floppyDiscIcon.png"));
      ImageView saveImageView = new ImageView(saveImage);
      saveImageView.setFitHeight(20);
      saveImageView.setFitWidth(20);
      saveButton.setGraphic(saveImageView);

      // create back button for metric scenes
      Button metricBackButton = new Button("Back");

      /**
       * Create layouts to house controls
       */

      // use a BorderPane layout (top, bottom, left, right, center) for search scene
      BorderPane root = new BorderPane();
      VBox leftSidePane = new VBox(5);
      VBox rightSidePane = new VBox(5);
      ListView<String> centerList;
      HBox bottomPane = new HBox(5);

      // use a BorderPane layout for the house metric scene, and two VBoxes for left and right
      BorderPane houseMetricLayout = new BorderPane();
      VBox leftHouseMetricPane = new VBox(houseParties);
      VBox rightHouseMetricPane = new VBox(houseGender);
      HBox metricBottomPane = new HBox();
      metricBottomPane.getChildren().add(metricBackButton);

      BorderPane senateMetricLayout = new BorderPane();
      VBox leftSenateMetricPane = new VBox(senateParties);
      VBox rightSenateMetricPane = new VBox(senateGender);

      // add to layout
      houseMetricLayout.setLeft(leftHouseMetricPane);
      houseMetricLayout.setRight(rightHouseMetricPane);
      houseMetricLayout.setBottom(metricBottomPane);
      metricBottomPane.setAlignment(Pos.BASELINE_LEFT);
      metricBottomPane.setPadding(new Insets(5));
      senateMetricLayout.setLeft(leftSenateMetricPane);
      senateMetricLayout.setRight(rightSenateMetricPane);
      senateMetricLayout.setBottom(metricBottomPane);

      // add initial list of items to centerList
      List<String> legNames = congress.getAllNames();
      legNames.sort(null);
      ObservableList<String> names = FXCollections.observableArrayList(legNames);
      centerList = new ListView<String>(names);

      // add controls to the layouts for searchScene
      leftSidePane.getChildren().addAll(bodyLabel, bodyBox, stateLabel, stateBox, partyLabel,
          partyBox, genderLabel, genderBox, submitButton, clearButton, backButton);
      rightSidePane.getChildren().addAll(seeSenateStatsButton, seeHouseStatsButton);
      bottomPane.getChildren().addAll(addLegislator, removeLegislator, saveButton);

      // add the inner layouts to the BorderPane for searchScene
      root.setLeft(leftSidePane);
      root.setRight(rightSidePane);
      root.setCenter(centerList);
      leftSidePane.setPadding(new Insets(5));
      rightSidePane.setPadding(new Insets(5));

      // BorderPane.setMargin(topPane, new Insets(0, 0, 0, 50));
      root.setBottom(bottomPane);
      bottomPane.setAlignment(Pos.BASELINE_CENTER);
      bottomPane.setPadding(new Insets(5));

      // use a VBox for main scene
      VBox mainLayout = new VBox(10);
      mainLayout.setAlignment(Pos.CENTER);

      // add buttons to the main layout
      mainLayout.getChildren().addAll(searchButton, seeHouseStatsButton, seeSenateStatsButton);

      // create scenes
      Scene searchScene = new Scene(root, 1000, 475);
      Scene mainScene = new Scene(mainLayout, 1000, 475);
      Scene houseMetricScene = new Scene(houseMetricLayout, 1000, 475);
      Scene senateMetricScene = new Scene(senateMetricLayout, 1000, 475);

      // background for mainScene
      // BackgroundImage mainBackImage =
      // new BackgroundImage(new Image(this.getClass().getResourceAsStream("flagBackground.jpg")),
      // null, null, null, null);
      // Background mainBackground = new Background(mainBackImage);

      searchScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
      mainScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
      // mainLayout.setBackground(mainBackground);

      // set actions for buttons
      searchButton.setOnAction(e -> primaryStage.setScene(searchScene));
      backButton.setOnAction(e -> primaryStage.setScene(mainScene));

      // House metric button
      seeHouseStatsButton.setOnAction(e -> primaryStage.setScene(houseMetricScene));

      // Senate metric button
      seeSenateStatsButton.setOnAction(e -> primaryStage.setScene(senateMetricScene));

      // metric back button
      metricBackButton.setOnAction(e -> primaryStage.setScene(searchScene));
      


      // set submit button click action TODO
      submitButton.setOnAction(e -> {
        // first clear the ListView
        centerList.getSelectionModel().clearSelection();

      });

      // private EventHandler to pass to the clearButton's action
      EventHandler<ActionEvent> clearAction = new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
          // get the list of nodes from the pane
          List<Node> nodes = leftSidePane.getChildren();

          // for each ComboBox in the pane, clear the selection
          for (Node node : nodes) {
            if (node instanceof ComboBox) {
              ((ComboBox<?>) node).getSelectionModel().clearSelection();
            }
          }
        }

      };

      // EventHandler to pass to removeLegislator's action
      EventHandler<ActionEvent> removeAction = new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
          Stage removeStage = new Stage();

          Alert removeConfirmationAlert = new Alert(AlertType.CONFIRMATION);
          removeConfirmationAlert.setTitle("Success!");
          removeConfirmationAlert.setContentText("The legislator was successfully removed.");

          Alert removeErrorAlert = new Alert(AlertType.ERROR);
          removeErrorAlert.setTitle("Error!");
          removeErrorAlert.setContentText("The legislator could not be removed.");

          // override the close request event to close this pop up and the alert
          removeConfirmationAlert.setOnCloseRequest(new EventHandler<DialogEvent>() {

            @Override
            public void handle(DialogEvent event) {
              // TODO Auto-generated method stub
              removeConfirmationAlert.close();
              removeStage.close();
            }

          });

          // make the Stage modal so the user can't click elsewhere
          removeStage.initModality(Modality.APPLICATION_MODAL);
          removeStage.initOwner(primaryStage);

          Button removeButton = new Button("Remove");
          Button cancelButton = new Button("Cancel");
          TextField firstName = new TextField();
          Label firstNameLabel = new Label("First Name:");
          TextField lastName = new TextField();
          Label lastNameLabel = new Label("Last Name:");

          FlowPane centerBox = new FlowPane(Orientation.VERTICAL);
          centerBox.setHgap(10);


          centerBox.getChildren().addAll(firstNameLabel, firstName, lastNameLabel, lastName);
          centerBox.setAlignment(Pos.CENTER);

          HBox buttonPane = new HBox(5);
          buttonPane.getChildren().addAll(removeButton, cancelButton);
          buttonPane.setAlignment(Pos.BASELINE_CENTER);
          buttonPane.setPadding(new Insets(5));

          BorderPane removeBox = new BorderPane();
          removeBox.setCenter(centerBox);
          removeBox.setBottom(buttonPane);

          // create scene to add layout to
          Scene removeScene = new Scene(removeBox, 300, 400);

          removeStage.setScene(removeScene);

          removeStage.show();

          cancelButton.setOnAction(e -> removeStage.close());
          removeButton.setOnAction(e -> {

            // if the new object is added successfully, show confirmation
            if (congress.removeLegislator(firstName.getText(), lastName.getText()) == true) {

              // show confirmation message
              removeConfirmationAlert.show();
            } else {

              // otherwise, show an error
              removeErrorAlert.show();
            }
          });
        }
      };

      // EventHandler to pass addLegislator's action
      EventHandler<ActionEvent> addAction = new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
          // create a new Stage
          Stage addStage = new Stage();

          Alert addErrorAlert = new Alert(AlertType.ERROR);
          addErrorAlert.setTitle("Error!");
          addErrorAlert.setContentText("The new legislator could not be added.");

          Alert addConfirmationAlert = new Alert(AlertType.CONFIRMATION);
          addConfirmationAlert.setTitle("Success!");
          addConfirmationAlert.setContentText("The new legislator was sucessfully added.");

          // override the close request event to close this pop up and the alert
          addConfirmationAlert.setOnCloseRequest(new EventHandler<DialogEvent>() {

            @Override
            public void handle(DialogEvent arg0) {
              addConfirmationAlert.close();
              addStage.close();

            }

          });

          // make the Stage modal so the user can't click elsewhere
          addStage.initModality(Modality.APPLICATION_MODAL);
          addStage.initOwner(primaryStage);
          // create controls for the form
          Button addButton = new Button("Add");
          Button cancelButton = new Button("Cancel");
          TextField firstName = new TextField();
          Label firstNameLabel = new Label("First Name:");
          TextField lastName = new TextField();
          Label lastNameLabel = new Label("Last Name:");

          // list of states for the State ComboBox
          ObservableList<String> states = FXCollections.observableArrayList("", "AL", "AK", "AZ",
              "AR", "CA", "CO", "CT", "DE", "FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS", "KY",
              "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "NE", "NV", "NH", "NJ", "NM", "NY",
              "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA",
              "WA", "WV", "WI", "WY");

          // two options for gender (male or female)
          ObservableList<String> genderList =
              FXCollections.observableArrayList("", "Male", "Female");

          // options for legislative body
          ObservableList<String> bodyList =
              FXCollections.observableArrayList("", "House", "Senate");

          // three options for party
          ObservableList<String> partyList =
              FXCollections.observableArrayList("", "Democrat", "Republican", "Independent");
          ComboBox<String> genderBox = new ComboBox<String>(genderList);
          Label genderLabel = new Label("Gender:");
          ComboBox<String> partyBox = new ComboBox<String>(partyList);
          Label partyLabel = new Label("Party:");
          ComboBox<String> stateBox = new ComboBox<String>(states);
          Label stateLabel = new Label("State:");

          ComboBox<String> bodyBox = new ComboBox<String>(bodyList);
          Label bodyLabel = new Label("Body:");

          FlowPane centerBox = new FlowPane(Orientation.VERTICAL);
          centerBox.setHgap(10);
          centerBox.getChildren().addAll(bodyLabel, bodyBox, firstNameLabel, firstName,
              lastNameLabel, lastName, genderLabel, genderBox, partyLabel, partyBox, stateLabel,
              stateBox);
          centerBox.setAlignment(Pos.CENTER);

          HBox buttonPane = new HBox(5);
          buttonPane.getChildren().addAll(addButton, cancelButton);
          buttonPane.setAlignment(Pos.BASELINE_CENTER);
          buttonPane.setPadding(new Insets(5));

          BorderPane addBox = new BorderPane();
          addBox.setCenter(centerBox);
          addBox.setBottom(buttonPane);

          // create scene to add layout to
          Scene addScene = new Scene(addBox, 300, 400);

          addStage.setScene(addScene);

          addStage.show();

          cancelButton.setOnAction(e -> addStage.close());
          addButton.setOnAction(e -> {

            // if the new object is added successfully, show confirmation
            if (congress.addLegislator(firstName.getText(), lastName.getText(),
                genderBox.getValue(), stateBox.getValue(), partyBox.getValue(),
                bodyBox.getValue()) == true) {

              // show confirmation message
              addConfirmationAlert.show();
            } else {

              // otherwise, show an error
              addErrorAlert.show();
            }
          });
        }
      };

      clearButton.setOnAction(clearAction);
      addLegislator.setOnAction(addAction);
      removeLegislator.setOnAction(removeAction);

      // add the scene to the stage
      primaryStage.setScene(mainScene);
      primaryStage.setTitle("Representation Tracker");


      // update the taskbar icon
      primaryStage.getIcons()
          .add(new Image(this.getClass().getResourceAsStream("capitolLogo.jpg")));
      primaryStage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }



  public static void main(String[] args) {
    launch(args);
  }
}
