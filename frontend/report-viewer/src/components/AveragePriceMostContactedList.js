import React, { Component } from 'react';

class AveragePriceMostContactedList extends Component {
    API_URL = "http://localhost:8080/reports/Avg30PercentMostContactedListingsPrice";
    state = { averagePriceMostContactedList: [] };

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
        fetch(this.API_URL)
            .then(res => res.json())
            .then((data) => {
                this.setState({ averagePriceMostContactedList: [...this.state.averagePriceMostContactedList, data] });
                this.render();
            })
            .catch(console.log)
    }
}

export default AveragePriceMostContactedList;