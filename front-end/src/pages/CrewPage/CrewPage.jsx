import {List, Stack, Typography} from "@mui/material";
import {MarineRow} from "../../components/MarineRow/MarineRow";
import {MarineDetails} from "../../components/MarineDetails/MarineDetails";
import {useShipQuery} from "../../hooks/useShipQuery";
import {useMarineQuery} from "../../hooks/useMarineQuery";
import {useNavigate, useParams} from "react-router-dom";
import React from "react";
import {useShipUnloadQuery} from "../../hooks/useUnloadFromShip";
import {SpeedDial, SpeedDialAction, SpeedDialIcon} from "@mui/lab";
import PublishIcon from '@mui/icons-material/Publish';
import {Download} from "@mui/icons-material";
import {Loader} from "../../components/Loader/Loader";

export const CrewPage = () => {
    let {shipId} = useParams();
    const {shipStatus, reloadShip} = useShipQuery(shipId)
    const {unloadStatus, unloadShip, resetUnloadShips} = useShipUnloadQuery(shipId)
    const {marineStatus, loadMarine, reset} = useMarineQuery()
    const navigate = useNavigate()

    if (shipStatus.isLoading || unloadStatus.isLoading) {
        return <Loader/>
    }

    if (unloadStatus.isSuccess) {
        resetUnloadShips()
        reloadShip()
    }

    return (<Stack>
        <Typography padding={2} fontWeight="bold" variant="h3">
            {shipStatus.data.name}
        </Typography>

        <List>
            {(shipStatus.data.marines ?? []).map(el => <MarineRow marine={el} onClick={() => {
                loadMarine(el.id)
            }}/>)}
            {((shipStatus.data.marines ?? []).length === 0) &&
                <Typography variant="h6" color={"grey"}>
                Ничего не найдено
            </Typography>}
        </List>
        {marineStatus.isSuccess && <MarineDetails allowUpdate refetch={() => {
            reloadShip()
        }} marine={marineStatus.data}
                                                  onClose={() => reset()}/>}
        {marineStatus.isLoading && (<Loader/>)}
        <SpeedDial
            sx={{position: 'fixed', bottom: 24, right: 24}}
            icon={<SpeedDialIcon/>}
            ariaLabel={"crew"}
        >
            <SpeedDialAction
                icon={<Download/>}
                tooltipTitle={"Load marine"}
                tooltipOpen
                onClick={() => navigate(`/marines?shipId=${shipId}`)}
            />
            <SpeedDialAction
                icon={<PublishIcon/>}
                tooltipTitle={"Unload marines"}
                tooltipOpen
                onClick={() => unloadShip()}
            />
        </SpeedDial>
    </Stack>)
}

