import React, { Component } from 'react';
import "./app.css";


// TODO: define a "page" enum type for all of the possible app pages
type page = {kind: "ChildView"} | {kind: "ParentView"} | {kind: "Login"}
type userType = "Child" | "Parent"
type joinType = "Join" | "Create"
type household = {name: string, joinCode: string, parents: string[], children: string[]}

type AppProps = {};  // no props

type AppState = {
  // TODO: create state to track page and any other data
  // that need to be communicated between components
  page: page
  userType: userType
  joinType?: joinType
  household?: household
};


/** Top-level component that displays the entire UI. */
export class App extends Component<AppProps, AppState> {
  constructor(props: AppProps) {
    super(props);

    // TODO: initialize state you create
    this.state = {page: {kind: "Login"}, userType: "Child"};
  }

  render = (): JSX.Element => {
    // TODO: update to return the correct component depending on which
    // page the app should render

    return this.renderLogin();
  };

  renderLogin = (): JSX.Element => {
    // TODO: add onClick properties to each of these <a> links
    // that cause the App to switch to the appropriate page view
    return <div>
      <h1>Login</h1>
      <label>User Type:</label>
      <select value={this.state.userType} onChange={this.setUserType}>
        <option>Child</option>
        <option>Parent</option>
      </select>
      <br></br>
      {this.state.userType === "Child" ? (
        <>
        <input type="text" placeholder="Join Code" />
        <button onClick={this.doLoginClick}>Login</button>
        </>
      ) : (
        <>
        <label>Join Or Create:</label>
        <select value={this.state.joinType} onChange={this.setJoinType}>
          <option>Join</option>
          <option>Create</option>
        </select>
        <br></br>
        {this.state.joinType === "Create" ? (
          <><input type="text" placeholder="New Household Name" /></>
        ) : (
          <>
          <input type="text" placeholder="Join Code" />
          <button onClick={this.doLoginClick}>Login</button>
          </>
          
        )}
        </>
      )}

    </div>;
  }

  doLoginClick = (): void => {
    // Get the value of the Join Code input field
    const joinCodeInput = document.querySelector('input[placeholder="Join Code"]') as HTMLInputElement;
    const joinCode = joinCodeInput?.value;

    if (!joinCode) {
      this.doLoginError('Join Code is required');
      return;
    }

    // Add joinCode as a query parameter
    const p = fetch(`/api/login?joinCode=${encodeURIComponent(joinCode)}`);
    p.then(this.doLoginResp)
     .catch((ex) => this.doLoginError('failed to connect', ex));
  }
  doCreateClick = (): void => {
    
  }

  doLoginResp = (res: Response): void => {
    if (res.status === 200) {
      console.log(res)
      const p = res.json();
      p.then(this.doLoginJson)
      p.catch((ex) => this.doLoginError('200 response is not JSON', ex));
    } else if (res.status === 400) {
      const p = res.text()
      p.then(this.doLoginError);
      p.catch((ex) => this.doLoginError('400 response is not text', ex));
    } else {
      this.doLoginError(`bad status code: ${res.status}`);
    }
  }
  doLoginJson = (data: any): void => {
    console.log("GOT STATUS: ")
    console.log(data.status)
    console.log("GOT HOUSEHOLD: ")
    console.log(data.household)

    if (data.status === true) {
      //change page
      this.setState({ household: data.household as household });
    } else {
      //make a warning message

    }
  }
  doLoginError = (msg: string, ex?: unknown): void => {
    console.error(`fetch of /api/login failed: ${msg}`)
    if (ex instanceof Error)
      throw ex;
  };

  setUserType = (event: React.ChangeEvent<HTMLSelectElement>): void => {
    this.setState({ userType: event.target.value as userType });
    this.setState({ joinType: "Join" });
  }
  setJoinType = (event: React.ChangeEvent<HTMLSelectElement>): void => {
    this.setState({ joinType: event.target.value as joinType });
  }
}
