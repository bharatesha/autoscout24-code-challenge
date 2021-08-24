import React, { Component } from 'react';
import AverageSellingPrice from './components/AverageSellingPrice';
import CarsDistribution from './components/CarsDistribution';
import AveragePriceMostContactedList from './components/AveragePriceMostContactedList';
import MostContactedListingPerMonth from './components/MostContactedListingPerMonth';
import FileUploader from './components/FileUploader';
import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';

class App extends Component {

  render() {
    return (
      <div>
        <h2>AutoScout24 Listing Report</h2>
        <FileUploader />
        <AverageSellingPrice />
        <CarsDistribution />
        <AveragePriceMostContactedList />
        <MostContactedListingPerMonth />
      </div>
    );
  }
}
export default App;