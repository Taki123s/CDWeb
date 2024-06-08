import React from "react";
import {
  BrowserRouter as Router,
  Routes,
  Route,
  Navigate,
} from "react-router-dom";
import "../App.css";
import MovieDetail from "../page/MovieDetail";
import ProductItem from "../page/ProductItem";
import AnimePage from "../component/Index";
import { CategoriesPage } from "../page/Categorie";
import { HeaderPage } from "../component/Header";
import ServicePack from "../component/ServicePack";
import MovieWatching from "../page/MovieWatching";
import LoginGoogle from "../component/LoginGoogleSuccess";
import LoginFacebook from "../component/LoginFacebookSuccess";
import PayPal from "../component/PayPal";
import ExecutePaymentComponent from "../component/ExecutePaymentComponent";
import Follow from "../page/Follow";
import History from "../component/history-packed";

function IndexApp() {
  return (
    <Router>
      <HeaderPage />
      <Routes>
        <Route path="/" element={<AnimePage />} />
        <Route path="/movie/:id" element={<MovieDetail />} />
        <Route path="/item" element={<ProductItem />} />
        <Route path="/history-packed" element={<History />} />
        <Route
          path="/categories/:idGenre/:nameGenre"
          element={<CategoriesPage />}
        />
        <Route path="/index" element={<AnimePage />} />
        <Route path="/servicePack" element={<ServicePack />} />
        <Route path="/login-google" element={<LoginGoogle />} />
        <Route path="/login-facebook" element={<LoginFacebook />} />
        <Route path="/execute-payment" element={<ExecutePaymentComponent />} />
        <Route
          path="/movie/watching/:movieId/:chapterId"
          element={<MovieWatching />}
        />
        <Route path="/follow" element={<Follow />} />
        <Route path="/watching/:id/:chapter" element={<MovieWatching />} />
        <Route path="/PayPal" element={<PayPal />} />
      </Routes>
    </Router>
  );
}

export default IndexApp;
