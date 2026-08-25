import {
    BrowserRouter,
    Routes,
    Route
} from "react-router-dom";

import Login from "../pages/Login/Login";
import Home from "../pages/Home/Home";
import Cadastrar from "../pages/Cadastrar/Cadastrar";


function AppRoutes() {

    return (
        <BrowserRouter>

            <Routes>

                <Route
                    path="/home"
                    element={<Home />}
                />

                <Route
                    path="/signin"
                    element={<Login />}
                />

                <Route
                    path="/signup"
                    element={<Cadastrar />}
                />

            </Routes>

        </BrowserRouter>
    );
}

export default AppRoutes;