import React from 'react';
import ReactGaugeChart from 'react-gauge-chart';

const getGaugeValue = (signal) => {
    switch (signal) {
        case 'Buy':
            return 0.75;
        case 'Neutral':
            return 0.5;
        case 'Sell':
            return 0.25;
        default:
            return 0.5;
    }
};
//Renders the gauge and points based on the value entered
const renderGauge = (signal, label) => {
    return (
        <div style={{width:'300px',height:'300px'}}>
            <h4>{label}</h4>
            <ReactGaugeChart
                id="gauge-chart"
                nrOfLevels={30}
                colors={['#ff0000', '#ffcc00', '#00ff00']}
                percent={getGaugeValue(signal)}
                textColor="#000000"
                needleColor="#000000"
                arcWidth={0.3}
                arcsLength={[0.33, 0.33, 0.33]}
            />
            <p>{signal}</p>
        </div>
    );
};

export default renderGauge;
