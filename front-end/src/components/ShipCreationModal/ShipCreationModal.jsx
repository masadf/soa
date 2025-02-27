import {Avatar, Box, Button, Modal, TextField} from "@mui/material";
import Grid from "@mui/material/Grid2";
import {LoadingButton} from "@mui/lab";
import {useState} from "react";
import {useShipCreateQuery} from "../../hooks/useShipsCreateQuery";

const style = {
    position: 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    bgcolor: 'background.paper',
    boxShadow: 24,
    p: 4,
};

export const ShipCreationModal = ({open, refetch, onClose}) => {
    const {creationStatus, createShip} = useShipCreateQuery();
    const [creatingRequest, setCreatingRequest] = useState({});

    if (creationStatus.isSuccess) {
        onClose()
        refetch()
    }

    return (
        <Modal open={open} onClose={onClose}>
            <Box sx={style}>
                <Grid container spacing={4}>
                    <Grid container columns={2} spacing={3}>
                        <Grid item size={1}>
                            <TextField onChange={(e) => {
                                setCreatingRequest({...creatingRequest, name: e.target.value})
                            }} variant="standard" label="Название" value={creatingRequest?.name}/>
                        </Grid>
                        <Grid item size={1} container direction="column" alignItems="center" spacing={1}>
                            <Avatar
                                variant={"rounded"}
                                sx={{width: "100%", height: "100%"}}
                                src={`${process.env.PUBLIC_URL}/static/images/avatar/ship.jpg`}
                            />
                        </Grid>
                    </Grid>
                    <Grid container columns={2} spacing={3} justifyContent="space-between" width={"100%"}>
                        <Grid spacing={2} container>
                            <LoadingButton variant={"contained"} loading={creationStatus.isLoading}
                                           onClick={() => createShip(creatingRequest)}>Создать</LoadingButton>
                            <Button variant={"contained"} onClick={onClose} color="inherit">Назад</Button>
                        </Grid>
                    </Grid>
                </Grid>
            </Box>
        </Modal>
    )
}