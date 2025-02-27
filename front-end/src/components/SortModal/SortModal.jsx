import {Box, Button, Chip, IconButton, MenuItem, Modal, Select} from "@mui/material";
import Grid from "@mui/material/Grid2";
import React, {useState} from "react";
import ArrowUpwardIcon from '@mui/icons-material/ArrowUpward';
import ArrowDownwardOutlinedIcon from '@mui/icons-material/ArrowDownwardOutlined';

const style = {
    position: 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    bgcolor: 'background.paper',
    width: "40vw",
    boxShadow: 24,
    p: 4,
};

export const SortModal = ({open, onClose, reloadMarines}) => {
    const [field, setField] = useState("name")
    const [isDescending, setDescending] = useState(true)
    const [sortAttributes, setSortAttributes] = useState([])

    return (<Modal open={open} onClose={onClose}>
        <Box sx={style}>
            <Grid container spacing={3}>
                <Grid container width={"100%"} direction={"row"} spacing={1}>
                    <Grid item>
                        <Select
                            onChange={(e) => {
                                setField(e.target.value)
                            }}
                            value={field}
                            variant={"standard"}
                        >
                            <MenuItem value={"name"}>Имя</MenuItem>
                            <MenuItem value={"category"}>Категория</MenuItem>
                            <MenuItem value={"health"}>Здоровье</MenuItem>
                            <MenuItem value={"coordinates.x"}>Координата X</MenuItem>
                            <MenuItem value={"coordinates.y"}>Координата Y</MenuItem>
                            <MenuItem value={"creationDate"}>Дата создания</MenuItem>
                            <MenuItem value={"weaponType"}>Тип оружия</MenuItem>
                            <MenuItem value={"meleeWeapon"}>Тип холодного оружия</MenuItem>
                            <MenuItem value={"chapter.name"}>Название главы</MenuItem>
                        </Select>
                    </Grid>
                    <Grid item>
                        <IconButton aria-label="descending" color={isDescending ? "primary" : ""} size="large"
                                    onClick={() => setDescending(true)}>
                            <ArrowUpwardIcon/>
                        </IconButton>
                        <IconButton aria-label="ascending" color={!isDescending ? "primary" : ""} size="large"
                                    onClick={() => setDescending(false)}>
                            <ArrowDownwardOutlinedIcon/>
                        </IconButton>
                    </Grid>
                </Grid>
                <Grid columns={4} spacing={1} container>
                    {sortAttributes.map(sorter => <Chip onClick={() => setSortAttributes(
                        sortAttributes.filter(attr => attr !== sorter)
                    )} label={sorter.field + (sorter.isDescending ? "(desc)" : "(asc)")}/>)}
                </Grid>
                <Grid container columns={2} spacing={3} justifyContent="space-between" width={"100%"}>
                    <Grid spacing={2} container>
                        <Button variant={"contained"}
                                onClick={() => {
                                    reloadMarines(null, sortAttributes.map(
                                        sorter => sorter.field + (sorter.isDescending ? "(desc)" : "(asc)")))
                                    onClose()
                                }}>Применить</Button>
                        <Button variant={"contained"} color={"secondary"}
                                disabled={sortAttributes.filter(attr => attr.field === field).length}
                                onClick={() =>
                                    setSortAttributes(
                                        [...sortAttributes, {
                                            field, isDescending
                                        }]
                                    )
                                }>Добавить</Button>
                    </Grid>
                    <Button variant={"contained"} onClick={onClose} color="inherit">Назад</Button>
                </Grid>
            </Grid>
        </Box>
    </Modal>)
}