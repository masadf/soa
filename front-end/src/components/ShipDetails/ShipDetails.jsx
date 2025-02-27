import {Avatar, Box, Button, Modal, Stack, TextField} from "@mui/material";
import Grid from "@mui/material/Grid2";
import {LoadingButton} from "@mui/lab";
import {useState} from "react";
import {useShipUpdateQuery} from "../../hooks/useShipsUpdateQuery";
import {useShipsDeleteQuery} from "../../hooks/useShipsDeleteQuery";
import {useNavigate} from "react-router-dom";

const style = {
    position: 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    bgcolor: 'background.paper',
    boxShadow: 24,
    p: 4,
};

export const ShipDetails = ({ship, onClose, refetch, reload}) => {
    const {deletingStatus, deleteShip} = useShipsDeleteQuery();
    const {updatingStatus, updateShip} = useShipUpdateQuery();
    const navigate = useNavigate()
    const [updatingRequest, setUpdatingRequest] = useState({
        name: ship.name,
    });

    if (deletingStatus.isSuccess) {
        onClose()
        refetch()
    }

    if (updatingStatus.isSuccess) {
        onClose()
        refetch()
    }

    return (
        <Modal open={ship} onClose={onClose}>
            <Box sx={style}>
                <Grid container spacing={4}>
                    <Stack container columns={1} spacing={3} width={"100%"}>
                        <Grid  width={"100%"}>
                            <TextField fullWidth={true} onChange={(e) => {
                                setUpdatingRequest({...updatingRequest, name: e.target.value})
                            }} variant="standard" label="Название" value={updatingRequest?.name ?? ship.name}/>
                        </Grid>
                        <Avatar
                            variant={"rounded"}
                            sx={{width: "100%", height: "256px"}}
                            src={`${process.env.PUBLIC_URL}/static/images/avatar/ship.jpg`}
                        />
                    </Stack>
                    <Stack spacing={1} width={"100%"}>
                        <LoadingButton variant={"contained"} loading={updatingStatus.isLoading}
                                       onClick={() => updateShip(ship.id, updatingRequest)}>Сохранить</LoadingButton>
                        <Button variant={"contained"} onClick={() => navigate(`/ships/${ship.id}/marines`)}
                                color="secondary">К команде</Button>
                        <LoadingButton variant={"contained"} loading={deletingStatus.isLoading}
                                       onClick={() => deleteShip(ship.id)} color="error">
                            Удалить
                        </LoadingButton>
                        <Button variant={"contained"} onClick={onClose} color="inherit">Назад</Button>

                    </Stack>
                </Grid>
            </Box>
        </Modal>
    )
}