import {Stack, Typography} from "@mui/material";
import {ShipRow} from "../../components/ShipRow/ShipRow";
import {useShipsQuery} from "../../hooks/useShipsQuery";
import {Alert, SpeedDial, SpeedDialIcon} from "@mui/lab";
import {ShipCreationModal} from "../../components/ShipCreationModal/ShipCreationModal";
import React, {useState} from "react";
import Grid from "@mui/material/Grid2";
import {ShipDetails} from "../../components/ShipDetails/ShipDetails";
import {Loader} from "../../components/Loader/Loader";

export const ShipPage = () => {
    const {ships, reloadShips} = useShipsQuery()
    const [isShipCreation, createShip] = useState(false)
    const [openedShip, openDetails] = useState(null)

    if (ships.isLoading) {
        return <Loader/>
    }

    if (ships.isFailed) {
        return <Alert severity="error" sx={{margin: 1}}>
            Сервис временно недоступен
        </Alert>
    }

    return (
        <Stack>
            <Grid>
                {ships.data.map(el =>
                    <ShipRow ship={el} onClick={() => openDetails(el)}/>
                )}
                {ships.data.length === 0 &&
                    <Typography variant="h6" color={"grey"}>
                        Ничего не найдено
                    </Typography>
                }
            </Grid>
            <SpeedDial
                onClick={() => createShip(true)}
                sx={{position: 'fixed', bottom: 24, right: 24}}
                icon={<SpeedDialIcon/>}
                ariaLabel={"ship creation"}
            />
            {!!openedShip &&
                <ShipDetails refetch={() => {
                    reloadShips()
                }} ship={openedShip}
                             onClose={() => openDetails(null)}/>}
            <ShipCreationModal
                refetch={() => {
                    reloadShips()
                }} open={isShipCreation}
                onClose={() => createShip(false)}
            />
        </Stack>
    )
}

