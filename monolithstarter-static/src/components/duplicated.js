import React, {Component} from 'react';
import {getDuplicateMessage} from "../actions/DuplicateAction";

class Duplicate extends Component {
  constructor(props) {
    super(props);
    this.state = {
      data: null,
    };
  }

  componentDidMount() {
    this._isMounted = true;
    getDuplicateMessage().then(data => {
      if (this._isMounted)
      this.setState({data: data});
    }).catch(() => {
      if (this._isMounted)
        this.setState({message: 'The server did not respond so...hello from the client!'});
    });
  }

  componentWillUnmount() {
    this._isMounted = false;
  }


  render() {
    if (this.state.data != null) {

      return (
        <div>
          <table>
            <thead>
            <tr>{Object.keys(this.state.data[0]).map((key, index) => {
              return <th key={key}>{key}</th>;
            })}</tr>
            </thead>
            <tbody>
            {this.state.data.map((row, index) => {
              return <tr key={index}>
                <RenderRow key={index} data={row} state={this.state}/>
              </tr>
            })}
            </tbody>
          </table>
        </div>
      );
    }
    return (
      <div>{'Table cannot be created...'}</div>
    );
  }
}

const RenderRow = (props) => {
  return Object.keys(props.state.data[0]).map((key, index) => {
    return <td key={props.data[key]}>{props.data[key]}</td>
  });
}


export default Duplicate;
