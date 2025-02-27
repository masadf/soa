import {Box, Button, Chip, MenuItem, Modal, Select, TextField} from "@mui/material";
import Grid from "@mui/material/Grid2";
import {useNavigate} from "react-router-dom";
import {useState} from "react";
import {getOperations, useTypes, variablesToExpressions} from "./filterUtils";

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

export const FilterModal = ({open, onClose, reloadMarines}) => {
    const navigate = useNavigate();
    const [field, setField] = useState("name")
    const [operation, setOperation] = useState("=")
    const [extraValue, setExtraValue] = useState(null)
    const {currentType} = useTypes(field, setExtraValue)
    const [filtrationAttributes, setFiltrationAttributes] = useState([])

    return (<Modal open={open} onClose={onClose}>
        <Box sx={style}>
            <Grid container spacing={3}>
                <Grid container width={"100%"} direction={"row"} spacing={1}>
                    <Grid item>
                        <Select
                            onChange={(e) => {
                                setOperation("=")
                                setField(e.target.value)
                                setExtraValue(null)
                            }}
                            value={field}
                            variant={"standard"}
                        >
                            <MenuItem value={"name"}>Имя</MenuItem>
                            <MenuItem value={"category"}>Категория</MenuItem>
                            <MenuItem value={"health"}>Здоровье</MenuItem>
                            <MenuItem value={"coordinates.x"}>Координата X</MenuItem>
                            <MenuItem value={"coordinates.y"}>Координата Y</MenuItem>
                            {/*<MenuItem value={"creationDate"}>Дата создания</MenuItem>*/}
                            <MenuItem value={"weaponType"}>Тип оружия</MenuItem>
                            <MenuItem value={"meleeWeapon"}>Тип холодного оружия</MenuItem>
                            <MenuItem value={"chapter.name"}>Название главы</MenuItem>
                        </Select>
                    </Grid>
                    <Grid item>
                        <Select
                            onChange={(e) => setOperation(e.target.value)}
                            variant={"standard"}
                            value={operation}
                        >
                            {getOperations(field).map((operation) => <MenuItem
                                value={operation}>{operation}</MenuItem>)}
                        </Select>
                    </Grid>
                    <Grid item>
                        {currentType.type === "input" ? <TextField
                            onChange={(e) => setExtraValue(e.target.value)}
                            value={extraValue}
                            placeholder={"Ваше значение"}
                            variant={"standard"}
                        /> : <Select
                            onChange={(e) => setExtraValue(e.target.value)}
                            variant={"standard"}
                            value={extraValue}
                        >
                            {currentType.values.map((variant) => <MenuItem value={variant}>{variant}</MenuItem>)}
                        </Select>}
                    </Grid>
                </Grid>
                <Grid columns={4} spacing={1} container>
                    {filtrationAttributes.map(filter => <Chip onClick={() => setFiltrationAttributes(
                        filtrationAttributes.filter(attr => attr.extraValue !== filter.extraValue || attr.field !== filter.field || attr.operation !== filter.operation)
                    )}
                                                              label={filter.field + filter.operation + filter.extraValue}/>)}
                </Grid>
                <Grid container columns={2} spacing={3} justifyContent="space-between" width={"100%"}>
                    <Grid spacing={2} container>
                        <Button variant={"contained"}
                                onClick={() => {
                                    let expressions = variablesToExpressions(filtrationAttributes)
                                    // navigate({
                                    //     pathname: "/marines",
                                    //     search: expressions.map(el => "filterBy=" + el).join("&")
                                    // })
                                    reloadMarines(expressions, null)
                                    onClose()
                                }}>Применить</Button>
                        <Button variant={"contained"} color={"secondary"}
                                disabled={filtrationAttributes.filter(attr => attr.extraValue === extraValue && attr.field === field && attr.operation === operation).length}
                                onClick={() =>
                                    setFiltrationAttributes(
                                        [...filtrationAttributes, {field, operation, extraValue}]
                                    )
                                }>Добавить</Button>
                    </Grid>
                    <Button variant={"contained"} onClick={onClose} color="inherit">Назад</Button>
                </Grid>
            </Grid>
        </Box>
    </Modal>)
}