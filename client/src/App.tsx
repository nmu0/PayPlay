import React, { Component } from 'react';
import "./app.css";


// TODO: define a "page" enum type for all of the possible app pages
type page = {kind: "Store"} | {kind: "Palette"} | {kind: "Canvas"} | {kind: "Directory"}

type AppProps = {};  // no props

type AppState = {
  // TODO: create state to track page and any other data
  // that need to be communicated between components
  page: page
};


/** Top-level component that displays the entire UI. */
export class App extends Component<AppProps, AppState> {
  constructor(props: AppProps) {
    super(props);

    // TODO: initialize state you create
    this.state = {page: {kind: "Directory"}};
  }

  render = (): JSX.Element => {
    // TODO: update to return the correct component depending on which
    // page the app should render

    return this.renderDirectory();
  };

  renderDirectory = (): JSX.Element => {
    // TODO: add onClick properties to each of these <a> links
    // that cause the App to switch to the appropriate page view
    return <div>
      <h1>Directory</h1>
      <p>ChatGPT GOGOOGO</p>
      <button onClick={this.doGetNamesClick}>TEST</button>
    </div>;
  }

  doGetNamesClick = (): void => {
    const p = fetch('/api/getNames');
    p.then(this.doGetNamesResp)
    p.catch((ex) => this.doGetNamesError('failed to connect', ex));
  
  }
  doGetNamesResp = (res: Response): void => {
    if (res.status === 200) {
      console.log(res)
      const p = res.json();
      p.then(this.doGetNamesJson)
      p.catch((ex) => this.doGetNamesError('200 response is not JSON', ex));
    } else if (res.status === 400) {
      const p = res.text()
      p.then(this.doGetNamesError);
      p.catch((ex) => this.doGetNamesError('400 response is not text', ex));
    } else {
      this.doGetNamesError(`bad status code: ${res.status}`);
    }
  }
  doGetNamesJson = (data: any): void => {
    const names: Array<string> = [];
    console.log("GOT NAMES: ")
    console.log(data.names)
    for (const name of data.names) {
      console.log(name)
      names.push(name)
    }
    //this.setState({files: names});
  }
  doGetNamesError = (msg: string, ex?: unknown): void => {
    console.error(`fetch of /api/getNames failed: ${msg}`)
    if (ex instanceof Error)
      throw ex;
  };
}
