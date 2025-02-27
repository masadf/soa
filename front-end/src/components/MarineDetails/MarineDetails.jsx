import {Avatar, Box, Button, CircularProgress, MenuItem, Modal, Select, Stack, TextField} from "@mui/material";
import Grid from "@mui/material/Grid2";
import {useMarinesDeleteQuery} from "../../hooks/useMarinesDeleteQuery";
import {LoadingButton} from "@mui/lab";
import {useState} from "react";
import {useMarinesUpdateQuery} from "../../hooks/useMarinesUpdateQuery";
import {useLoadToShip} from "../../hooks/useLoadToShip";
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

export const MarineDetails = ({marine, onClose, refetch, shipId, allowDelete, allowUpdate}) => {
    const {deletingStatus, deleteMarine} = useMarinesDeleteQuery();
    const {updatingStatus, updateMarine} = useMarinesUpdateQuery();
    const {loadStatus, loadToShip} = useLoadToShip(shipId);
    const navigate = useNavigate();
    const [updatingRequest, setUpdatingRequest] = useState({
        name: marine.name,
        coordinates: {
            x: marine.coordinates.x,
            y: marine.coordinates.y
        },
        health: marine.health,
        category: marine.category,
        weaponType: marine.weaponType,
        meleeWeapon: marine.meleeWeapon,
        chapterName: marine.chapter.name
    });

    if (deletingStatus.isSuccess) {
        onClose()
        refetch()
    }

    if (updatingStatus.isSuccess) {
        onClose()
        refetch()
    }

    if (loadStatus.isSuccess) {
        navigate(`/ships/${shipId}/marines`)
    }

    return (
        <Modal open={marine} onClose={onClose}>
            <Box sx={style}>
                <Grid container spacing={4}>
                    <Grid container columns={2} spacing={3}>
                        <Grid item size={1}>
                            <Stack spacing={2}>
                                {updatingStatus.isSuccess && <CircularProgress/>}
                                <TextField onChange={(e) => {
                                    setUpdatingRequest({...updatingRequest, name: e.target.value})
                                }} variant="standard" label="Имя" value={updatingRequest?.name ?? marine.name}/>
                                <Select
                                    value={updatingRequest?.category ?? marine.category}
                                    onChange={(e) => {
                                        setUpdatingRequest({...updatingRequest, category: e.target.value})
                                    }}
                                    label="Категория"
                                    variant={"standard"}
                                >
                                    <MenuItem value={"AGGRESSOR"}>AGGRESSOR</MenuItem>
                                    <MenuItem value={"SUPPRESSOR"}>SUPPRESSOR</MenuItem>
                                    <MenuItem value={"TERMINATOR"}>TERMINATOR</MenuItem>
                                    <MenuItem value={"HELIX"}>HELIX</MenuItem>
                                    <MenuItem value={"APOTHECARY"}>APOTHECARY</MenuItem>
                                </Select>
                                <TextField onChange={(e) => {
                                    setUpdatingRequest({...updatingRequest, health: e.target.value})
                                }} variant="standard" label="Здоровье"
                                           value={updatingRequest?.health ?? marine.health}/>
                                <TextField onChange={(e) => {
                                    setUpdatingRequest({
                                        ...updatingRequest,
                                        coordinates: {...updatingRequest.coordinates, x: e.target.value}
                                    })
                                }} variant="standard" label="Координата X"
                                           value={updatingRequest?.coordinates?.x ?? marine.coordinates.x}/>
                                <TextField onChange={(e) => {
                                    setUpdatingRequest({
                                        ...updatingRequest,
                                        coordinates: {...updatingRequest.coordinates, y: e.target.value}
                                    })
                                }} variant="standard" label="Координата Y"
                                           value={updatingRequest?.coordinates?.y ?? marine.coordinates.y}/>
                                <TextField variant="standard" label="Дата создания" value={marine.creationDate}/>
                                <Select
                                    value={updatingRequest?.weaponType ?? marine.weaponType}
                                    onChange={(e) => {
                                        setUpdatingRequest({...updatingRequest, weaponType: e.target.value})
                                    }}
                                    label="Тип оружия"
                                    variant={"standard"}
                                >
                                    <MenuItem value={"HEAVY_BOLTGUN"}>HEAVY_BOLTGUN</MenuItem>
                                    <MenuItem value={"BOLT_PISTOL"}>BOLT_PISTOL</MenuItem>
                                    <MenuItem value={"PLASMA_GUN"}>PLASMA_GUN</MenuItem>
                                    <MenuItem value={"COMBI_PLASMA_GUN"}>COMBI_PLASMA_GUN</MenuItem>
                                    <MenuItem value={"INFERNO_PISTOL"}>INFERNO_PISTOL</MenuItem>
                                </Select>
                                <Select
                                    value={updatingRequest?.meleeWeapon ?? marine.meleeWeapon}
                                    onChange={(e) => {
                                        setUpdatingRequest({...updatingRequest, meleeWeapon: e.target.value})
                                    }}
                                    label="Тип холодного оружия"
                                    variant={"standard"}
                                >
                                    <MenuItem value={"POWER_SWORD"}>POWER_SWORD</MenuItem>
                                    <MenuItem value={"CHAIN_AXE"}>CHAIN_AXE</MenuItem>
                                    <MenuItem value={"MANREAPER"}>MANREAPER</MenuItem>
                                    <MenuItem value={"POWER_FIST"}>POWER_FIST</MenuItem>
                                </Select>
                            </Stack>
                        </Grid>
                        <Grid item size={1} container direction="column" alignItems="center" spacing={1}>
                            <Grid item>
                                <Avatar
                                    variant={"rounded"}
                                    sx={{width: "100%", height: "100%"}}
                                    src={`${process.env.PUBLIC_URL}/static/images/avatar/${updatingRequest?.category ?? marine.category}.jpg`}
                                />
                            </Grid>
                            <Grid item container width="100%">
                                <Grid item size={1}>
                                    <Avatar
                                        variant={"rounded"}
                                        sx={{width: "100%", height: "100%"}}
                                        src={`${process.env.PUBLIC_URL}/static/images/weapon/${updatingRequest?.weaponType ?? marine.weaponType}.jpg`}
                                    />
                                </Grid>
                                <Grid item size={1}>
                                    <Avatar
                                        variant={"rounded"}
                                        sx={{width: "100%", height: "100%"}}
                                        src={`${process.env.PUBLIC_URL}/static/images/meleeWeapon/${updatingRequest?.meleeWeapon ?? marine.meleeWeapon}.jpg`}
                                    />
                                </Grid>
                            </Grid>

                        </Grid>
                    </Grid>
                    <Grid container columns={2} spacing={3} justifyContent="space-between" width={"100%"}>
                        <Grid spacing={2} container>
                            {allowUpdate &&
                                <LoadingButton variant={"contained"} loading={updatingStatus.isLoading}
                                               onClick={() => updateMarine(marine.id, updatingRequest)}>Сохранить</LoadingButton>
                            }
                            {!!shipId &&
                                <LoadingButton variant={"contained"} loading={loadStatus.isLoading}
                                               onClick={() => loadToShip(marine.id)}>Загрузить в корабль</LoadingButton>
                            }
                            <Button variant={"contained"} onClick={onClose} color="inherit">Назад</Button>
                        </Grid>
                        {allowDelete &&
                            <LoadingButton variant={"contained"} loading={deletingStatus.isLoading}
                                           onClick={() => deleteMarine(marine.id)} color="error">
                                Удалить
                            </LoadingButton>
                        }
                    </Grid>
                </Grid>
            </Box>
        </Modal>
    )
}