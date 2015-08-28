/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 by luxe - https://github.com/de-luxe - BURST-LUXE-ZDVD-CX3E-3SM58
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software
 * is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package burstcoin.address.generator.gui;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.NodeOrientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * The type Generator app view.
 */
public class GeneratorAppView
  extends BorderPane
{
  /**
   * The interface Action.
   */
  protected interface Action
  {
    /**
     * On tab select.
     *
     * @param name the name
     */
    void onTabSelect(String name);
  }

  private final TabPane tabPane;

  private Tab currentTab;
  private Map<String, Tab> tabLookup;

  private Action action;

  /**
   * Instantiates a new Generator app view.
   *
   * @param action the action
   */
  public GeneratorAppView(Action action)
  {
//    AeroFX.style();
//    AquaFx.style();

    tabLookup = new HashMap<>();
    this.action = action;
    ToolBar mainBar = new ToolBar();
    Label appLabel = new Label("BURST Address Generator");
    mainBar.getItems().add(appLabel);

    tabPane = new TabPane();

    ToolBar statusBar = new ToolBar();
    statusBar.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
    statusBar.getItems().add(new Label("Version: 0.2.3-SNAPSHOT"));

    setTop(mainBar);
    setCenter(tabPane);
    setBottom(statusBar);
  }

  /**
   * Sets current tab.
   *
   * @param currentTab the current tab
   */
  public void setCurrentTab(Tab currentTab)
  {
    this.currentTab = currentTab;
  }

  /**
   * Show tab.
   *
   * @param tabName the tab name
   */
  public void showTab(String tabName)
  {
    Tab tab = tabLookup.get(tabName);
    if(tab != null)
    {

      tabPane.getSelectionModel().select(tab);
    }
  }

  /**
   * Add content.
   *
   * @param name the name
   * @param content the content
   * @return the string
   */
  public String addContent(final String name, Node content)
  {

    Tab tab = new Tab(name);
    tab.setClosable(false);

    tab.setContent(content);
    tab.setOnSelectionChanged(new EventHandler<Event>()
    {
      @Override
      public void handle(Event event)
      {
        if(!event.getTarget().equals(currentTab))
        {
          setCurrentTab((Tab) event.getTarget());

          if(action != null)
          {
            action.onTabSelect(tab.getText());
          }
        }
      }
    });
    tabPane.getTabs().add(tab);
    String tabKey = UUID.randomUUID().toString();
    tabLookup.put(tabKey, tab);

    return tabKey;
  }

  /**
   * Add to.
   *
   * @param primaryStage the primary stage
   */
  public void addTo(Stage primaryStage)
  {
    primaryStage.setTitle("BURST");
//    primaryStage.initStyle(StageStyle.TRANSPARENT);
//    primaryStage.setFullScreen(true);
//    primaryStage.setFullScreenExitKeyCombination(KeyCombination.keyCombination("ALT_X"));
    StackPane root = new StackPane();
    root.getChildren().add(this);

    Scene scene = new Scene(root, 595, 650);

    primaryStage.setScene(scene);
    primaryStage.show();
  }

  /**
   * Gets tab.
   *
   * @param uuid the uuid
   * @return the tab
   */
  public Tab getTab(String uuid)
  {
    return tabLookup.get(uuid);
  }
}
