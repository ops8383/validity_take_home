import React, { Component } from 'react';
import { Route, Switch } from 'react-router-dom';
import './App.scss';
import HelloPage from "./pages/HelloPage";
import HomePage from "./pages/HomePage";
import deduplicatedPage from "./pages/dedupicatedPage";
import duplicatedPage from "./pages/duplicatedPage";

class App extends Component {
  render() {
    return (
      <Switch>
        <Route key="home" path="/" exact={true} component={HomePage} />
        <Route key="hello" path="/hello" exact={true} component={HelloPage} />
        <Route key="deduplicate" path="/deduplicate" exact={true} component={deduplicatedPage} />
        <Route key="duplicate" path="/duplicate" exact={true} component={duplicatedPage} />
      </Switch>
    );
  }
}


export default App;
