import React, { Component } from 'react';

class MostContactedListingPerMonth extends Component {

    constructor(props){
      super(props);
      this.state = { mostContactedPerMonth: [] };
      this.apiUrl = process.env.REACT_APP_REPORT_SERVICE_API_URL+'Top5ListingsPerMonth';
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
                            <div id={index} key={mostContactedlistings.monthYear}>
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
                                                <tr key={listings.listingId}>
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
                    }) : <div colSpan="5">Loading...</div>}
                </div>
            </div>
        );

    }
    componentDidMount() {
        fetch(this.apiUrl)
            .then(res => res.json())
            .then((data) => {
                this.setState({ mostContactedPerMonth: data });
            })
            .catch(console.log)
    }
}

export default MostContactedListingPerMonth;