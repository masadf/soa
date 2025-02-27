import {Avatar, Box, Button, MenuItem, Modal, Select, Stack, TextField} from "@mui/material";
import Grid from "@mui/material/Grid2";
import {LoadingButton} from "@mui/lab";
import {useState} from "react";
import {useMarinesCreateQuery} from "../../hooks/useMarinesCreateQuery";

const style = {
    position: 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    bgcolor: 'background.paper',
    boxShadow: 24,
    p: 4,
};
const requestDraft = {
    category: "AGGRESSOR",
    weaponType: "HEAVY_BOLTGUN",
    meleeWeapon: "POWER_SWORD"
}

export const MarineCreationModal = ({open, refetch, onClose}) => {
    const {creationStatus, createMarine} = useMarinesCreateQuery();
    const [creatingRequest, setCreatingRequest] = useState(requestDraft);

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
                            <Stack spacing={2}>
                                <TextField onChange={(e) => {
                                    setCreatingRequest({...creatingRequest, name: e.target.value})
                                }} variant="standard" label="Имя" value={creatingRequest?.name}/>
                                <Select
                                    value={creatingRequest?.category}
                                    onChange={(e) => {
                                        setCreatingRequest({...creatingRequest, category: e.target.value})
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
                                    setCreatingRequest({...creatingRequest, health: e.target.value})
                                }} variant="standard" label="Здоровье"
                                           value={creatingRequest?.health}/>
                                <TextField onChange={(e) => {
                                    setCreatingRequest({
                                        ...creatingRequest,
                                        coordinates: {...creatingRequest.coordinates, x: e.target.value}
                                    })
                                }} variant="standard" label="Координата X"
                                           value={creatingRequest?.coordinates?.x}/>
                                <TextField onChange={(e) => {
                                    setCreatingRequest({
                                        ...creatingRequest,
                                        coordinates: {...creatingRequest.coordinates, y: e.target.value}
                                    })
                                }} variant="standard" label="Координата Y"
                                           value={creatingRequest?.coordinates?.y}/>
                                <Select
                                    value={creatingRequest?.weaponType}
                                    onChange={(e) => {
                                        setCreatingRequest({...creatingRequest, weaponType: e.target.value})
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
                                    value={creatingRequest?.meleeWeapon}
                                    onChange={(e) => {
                                        setCreatingRequest({...creatingRequest, meleeWeapon: e.target.value})
                                    }}
                                    label="Тип холодного оружия"
                                    variant={"standard"}
                                >
                                    <MenuItem value={"POWER_SWORD"}>POWER_SWORD</MenuItem>
                                    <MenuItem value={"CHAIN_AXE"}>CHAIN_AXE</MenuItem>
                                    <MenuItem value={"MANREAPER"}>MANREAPER</MenuItem>
                                    <MenuItem value={"POWER_FIST"}>POWER_FIST</MenuItem>
                                </Select>
                                <TextField onChange={(e) => {
                                    setCreatingRequest({...creatingRequest, chapterName: e.target.value})
                                }} variant="standard" label="Глава"
                                           value={creatingRequest?.chapterName}/>

                            </Stack>
                        </Grid>
                        <Grid item size={1} container direction="column" alignItems="center" spacing={1}>
                            <Grid item>
                                <Avatar
                                    variant={"rounded"}
                                    sx={{width: "100%", height: "100%"}}
                                    src={`${process.env.PUBLIC_URL}/static/images/avatar/${creatingRequest?.category}.jpg`}
                                />
                            </Grid>
                            <Grid item container width="100%">
                                <Grid item size={1}>
                                    <Avatar
                                        variant={"rounded"}
                                        sx={{width: "100%", height: "100%"}}
                                        src={`${process.env.PUBLIC_URL}/static/images/weapon/${creatingRequest?.weaponType}.jpg`}
                                    />
                                </Grid>
                                <Grid item size={1}>
                                    <Avatar
                                        variant={"rounded"}
                                        sx={{width: "100%", height: "100%"}}
                                        src={`${process.env.PUBLIC_URL}/static/images/meleeWeapon/${creatingRequest?.meleeWeapon}.jpg`}
                                    />
                                </Grid>
                            </Grid>

                        </Grid>
                    </Grid>
                    <Grid container columns={2} spacing={3} justifyContent="space-between" width={"100%"}>
                        <Grid spacing={2} container>
                            <LoadingButton variant={"contained"} loading={creationStatus.isLoading}
                                           onClick={() => createMarine(creatingRequest)}>Создать</LoadingButton>
                            <Button variant={"contained"} onClick={onClose} color="inherit">Назад</Button>
                        </Grid>
                    </Grid>
                </Grid>
            </Box>
        </Modal>
    )
}