import React, { Component } from 'react';


class CarsDistribution extends Component {
  API_URL = "http://localhost:8080/reports/MakeDistributionPercent";
  state = { carsDistributionPercent: [] };

  render() {

    return (
      <div id="cars-distribution-report">
        <div className="App">
          <nav className="navbar navbar-light bg-light">
            <a className="navbar-brand" href="./">Distribution (in percent) of available cars by Make</a>
          </nav>
          <table className="table">
            <thead>
              <tr>
                <th>#</th>
                <th>Make</th>
                <th>Distribution</th>
              </tr>
            </thead>
            <tbody>
              {(this.state.carsDistributionPercent.length > 0) ? this.state.carsDistributionPercent[0].map((cars, index) => {
                return (
                  <tr key={index}>
                    <td>{index + 1}</td>
                    <td>{cars.make}</td>
                    <td>{cars.distributionPercent}</td>
                  </tr>
                )
              }) : <tr><td colSpan="5">Loading...</td></tr>}
            </tbody>
          </table>
        </div>
      </div>
    );
  }
  componentDidMount() {

    fetch(this.API_URL)
      .then(res => res.json())
      .then((data) => {
        this.setState({ carsDistributionPercent: [...this.state.carsDistributionPercent, data] });
        this.render();
      })
      .catch(console.log)
  }
}

export default CarsDistribution;