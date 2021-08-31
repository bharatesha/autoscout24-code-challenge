import React, { Component } from 'react';

class MostContactedListingPerMonth extends Component {

    API_URL = "http://localhost:8080/reports/Top5ListingsPerMonth";

    constructor(props){
      super(props);
      this.state = { mostContactedPerMonth: [] };
    }

    render() {
        return (
            <div id="cars-distribution-report">
                <div className="App">
                    <nav className="navbar navbar-light bg-light">
                        <a className="navbar-brand" href="./">The Top 5 most contacted listings per Month</a>
                    </nav>
                    {(this.state.mostContactedPerMonth.length > 0) ? this.state.mostContactedPerMonth.map((mostContactedlistings, index) => {
                        return (
                            <div id={index}>
                                <h4>{mostContactedlistings.monthYear}</h4>
                                <table className="table">
                                    <thead>
                                        <tr>
                                            <th>Ranking</th>
                                            <th>Listing ID</th>
                                            <th>Make</th>
                                            <th>Selling Price</th>
                                            <th>Mileage</th>
                                            <th>Total Amount of Contacts</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        {mostContactedlistings.listings.map((listings, i) => {
                                            return (
                                                <tr key={i}>
                                                    <td>{listings.ranking}</td>
                                                    <td>{listings.listingId}</td>
                                                    <td>{listings.make}</td>
                                                    <td>{listings.sellingPrice}</td>
                                                    <td>{listings.mileage}</td>
                                                    <td>{listings.totalAmountOfContacts}</td>
                                                </tr>
                                            )
                                        })
                                        }
                                    </tbody>
                                </table>
                            </div>
                        )
                    }) : <tr><td colSpan="5">Loading...</td></tr>}
                </div>
            </div>
        );

    }
    componentDidMount() {
        fetch(this.API_URL)
            .then(res => res.json())
            .then((data) => {
                this.setState({ mostContactedPerMonth: data });
            })
            .catch(console.log)
    }
}

export default MostContactedListingPerMonth;