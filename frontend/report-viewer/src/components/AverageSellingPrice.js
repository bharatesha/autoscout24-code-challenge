import React, { Component } from 'react';

class AverageSellingPrice extends Component {
    
    constructor(props){
       super(props);
       this.state = { averageSellingPrice: [] };
       this.apiUrl= process.env.REACT_APP_REPORT_SERVICE_API_URL+'AvgListingPrice';
    }

    render() {
        return (
            <div id="average-selling-report">
                <div className="App">
                    <nav className="navbar navbar-light bg-light">
                        <a className="navbar-brand" href="./">Average Listing Selling Price per Seller Type</a>
                    </nav>
                    <table className="table">
                        <thead>
                            <tr>
                                <th>#</th>
                                <th>Seller Type</th>
                                <th>Average in Euro</th>
                            </tr>
                        </thead>
                        <tbody>
                            {(this.state.averageSellingPrice.length > 0) ? this.state.averageSellingPrice.map((listing, index) => {
                                return (
                                    <tr key={index}>
                                        <td>{index + 1}</td>
                                        <td>{listing.sellerType}</td>
                                        <td>{listing.averagePrice}</td>
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
                this.setState({ averageSellingPrice: data });
            })
            .catch(console.log)
    }
}

export default AverageSellingPrice;