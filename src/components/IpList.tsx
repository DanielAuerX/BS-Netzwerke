import React from "react";
import { IState as Props } from "../App";

interface IProps {
    addresses: Props["addresses"]
}
function List({addresses}: IProps){
    const renderList = (): JSX.Element[] => {
        return addresses.map(addresses => {
            return (
                <li className="List">
                    <div className="List-header"></div>
                    <p>{addresses.ip}</p>
                    <p>{addresses.mask}</p>
                </li>
            )
        })
    }

    const toyExists = addresses.length > 0 ? 'list-of-stored-ips has-content' : 'list-of-stored-ips';

    return (
        <div className={toyExists}>
            <ul>
                {renderList()}
            </ul>
        </div>
    )
}

export default List