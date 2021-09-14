import React, { Component } from 'react';

class AveragePriceMostContactedList extends Component {

    constructor(props){
      super(props);
      this.state = { averagePriceMostContactedList: [] };
      this.apiUrl= process.env.REACT_APP_REPORT_SERVICE_API_URL+'Avg30PercentMostContactedListingsPrice';
    }

    render() {

        return (
            <div id="cars-distribution-report">
                <div className="App">
                    <nav className="navbar navbar-light bg-light">
                        <a className="navbar-brand" href="./">Average price of the 30% most contacted listings</a>
                    </nav>
                    <table className="table">
                        <thead>
                            <tr>
                                <th>#</th>
                                <th>Average Price</th>
                            </tr>
                        </thead>
                        <tbody>
                            {(this.state.averagePriceMostContactedList.length > 0) ? this.state.averagePriceMostContactedList.map((averageprice, index) => {
                                return (
                                    <tr key={index}>
                                        <td>{index + 1}</td>
                                        <td>{averageprice.price}</td>
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
        fetch(this.apiUrl)
            .then(res => res.json())
            .then((data) => {
                this.setState({ averagePriceMostContactedList: [ data] });
            })
            .catch(console.log)
    }
}

export default AveragePriceMostContactedList;