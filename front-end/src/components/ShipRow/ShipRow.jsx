import {Avatar, ListItemAvatar, ListItemButton} from "@mui/material";
import Grid from "@mui/material/Grid2";


export const ShipRow = ({ship, onClick}) => {
    return (
        <ListItemButton onClick={onClick}>
            <Grid direction="row" justifyContent={"space-between"} container sx={{
                width: "100%",
            }}>
                <Grid container spacing={4}>
                    <ListItemAvatar>
                        <Avatar
                            variant={"rounded"}
                            sx={{width: 256, height: 256}}
                            src={`${process.env.PUBLIC_URL}/static/images/avatar/ship.jpg`}
                        />
                    </ListItemAvatar>
                    <Grid item sx={{fontWeight: "bold", fontSize: 26}} alignContent="center">
                        {ship.name}
                    </Grid>
                </Grid>
            </Grid>
        </ListItemButton>
    )
}