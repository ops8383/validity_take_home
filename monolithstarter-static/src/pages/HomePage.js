import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';

class HomePage extends Component {
  render() {
    return (
        <div className="home-page">
          <div><Link to="/hello">Click to see hello message</Link></div>
          <div><Link to="/deduplicate">Click to see deduplicated Items</Link></div>
          <div><Link to="/duplicate">Click to see duplicate Items</Link></div>
        </div>
    );
  }
}


export default withRouter(HomePage);
