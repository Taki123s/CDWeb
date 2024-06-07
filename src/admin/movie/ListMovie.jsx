import React, { useEffect, useState } from "react";
import DataTable from "react-data-table-component";
import { Checkbox } from "@mui/material";
import ArrowDownward from "@mui/icons-material/ArrowDownward";
import Button from "@mui/material/Button";
import { parse, format } from "date-fns";
import Modal from "react-modal";
import Swal from "sweetalert2";
import { adminListMovie, deleteMovie } from "../../service/MovieServices";
import { Link } from "react-router-dom";
import { Loading } from "../../component/Loading";
Modal.setAppElement("#root");

export const ListMovie = () => {
  const [movies, setMovies] = useState([]);
  const [isOpen, setIsOpen] = useState(false);
  const [modalContent, setModalContent] = useState("");
  const [isUploading, setIsUploading] = useState(false);
  useEffect(() => {
    adminListMovie()
      .then((response) => {
        setMovies(response.data);
      })
      .catch((error) => {
        Swal.fire({
          title: "Lỗi",
          text: error.response.data.message,
          icon: "error",
          timer: 2000,
          showConfirmButton: false,
        });
      });
  }, []);

  const openModal = (content) => {
    setModalContent(content);
    setIsOpen(true);
  };

  const closeModal = () => {
    setIsOpen(false);
    setModalContent("");
  };

  const handleDelete = (id) => {
    Swal.fire({
      title: "Xác nhận",
      html: `Bạn có đồng ý xóa Movie với id: ${id} này không?`,
      icon: "warning",
      showCancelButton: true,
      confirmButtonText: "Đồng ý",
      cancelButtonText: "Hủy bỏ",
    }).then((result) => {
      if (result.isConfirmed) {
        setIsUploading(true)
        deleteMovie(id)
          .then((response) => {
            setIsUploading(false)
            setMovies((prevMovies) =>
              prevMovies.filter((movie) => movie.id !== id)
            );
            Swal.fire({
              title: "Thành công",
              text: response.data,
              icon: "success",
              timer: 2000,
              showConfirmButton: false,
            });
          })
          .catch((error) => {
            setIsUploading(false)
            Swal.fire({
              title: "Lỗi",
              text: error.response.data || "Lỗi kết nối",
              icon: "error",
              timer: 2000,
              showConfirmButton: false,
            });
          });
      }
    });
  };

  const handleEdit = (id) => {};

  const columns = [
    {
      id: 1,
      name: "Số thứ tự",
      selector: (row, index) => index + 1,
      reorder: true,
    },
    {
      id: 2,
      name: "Id",
      selector: (row) => row.id,
      sortable: true,
      reorder: true,
    },
    {
      id: 3,
      name: "Name",
      cell: (row) => (
        <div
          data-tag="allowRowEvents"
          style={{
            whiteSpace: "nowrap",
            overflow: "hidden",
            textOverflow: "ellipsis",
          }}
          key={row.id}
          onClick={() => openModal(row.name)}
        >
          {row.name}
        </div>
      ),
      sortable: true,
      reorder: true,
    },
    {
      id: 4,
      name: "Avatar",
      cell: (row) => (
        <div>
          <img
            src={row.avatarMovie}
            key={row.id}
            style={{ width: "100%", height: "100%" }}
          />
        </div>
      ),
    },
    {
      id: 5,
      name: "Trailer",
      cell: (row) => (
        <div>
          <button
            className="btn btn-outline-dark"
            onClick={() =>
              openModal(
                <div>
                  <iframe
                    width="100%"
                    height="100%"
                    src={row.trailer}
                    title={row.name + " trailer"}
                    frameBorder="0"
                    allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share"
                    referrerPolicy="strict-origin-when-cross-origin"
                    allowFullScreen
                  ></iframe>
                </div>
              )
            }
          >
            Click to view
          </button>
        </div>
      ),
    },
    {
      id: 6,
      name: "VN Descriptions",
      cell: (row) => (
        <div>
          <button
            className="btn btn-outline-dark"
            onClick={() => openModal(row.vietnameseDescriptions)}
          >
            Click to view
          </button>
        </div>
      ),
    },
    {
      id: 7,
      name: "EN Descriptions",
      cell: (row) => (
        <div>
          <div
            className="btn btn-outline-dark"
            onClick={() => openModal(row.englishDescriptions)}
          >
            Click to view
          </div>
        </div>
      ),
    },
    {
      id: 8,
      name: "Producer",
      cell: (row) => (
        <div
          data-tag="allowRowEvents"
          style={{
            whiteSpace: "nowrap",
            overflow: "hidden",
            textOverflow: "ellipsis",
          }}
          key={row.id}
          title={row.producer}
        >
          {row.producer}
        </div>
      ),
    },
    {
      id: 9,
      name: "Series",
      selector: (row) => row.series,
    },
    {
      id: 10,
      name: "Series Descriptions",
      selector: (row) => row.seriesDescriptions,
    },
    {
      id: 11,
      name: "Genres",
      cell: (row) => (
        <div>
          {row.genres.map((genre, index) => (
            <span
              className="btn btn-outline-danger ml-2 hoverWhite"
              key={index}
            >
              {genre.description}
            </span>
          ))}
        </div>
      ),
    },
    {
      id: 12,
      name: "Total Chapters",
      selector: (row) => row.totalChapters,
      sortable: true,
      reorder: true,
    },
    {
      id: 13,
      name: "Current Chapters",
      selector: (row) => row.currentChapters,
      sortable: true,
      reorder: true,
    },
    {
      id: 14,
      name: "View",
      selector: (row) => row.views,
      sortable: true,
      reorder: true,
    },
    {
      id: 15,
      name: "Rate",
      selector: (row) => row.rates,
      sortable: true,
      reorder: true,
    },
    {
      id: 16,
      name: "Follows",
      selector: (row) => row.follows,
      sortable: true,
      reorder: true,
    },
    {
      id: 17,
      name: "Create At",
      cell: (row) => (
        <div
          data-tag="allowRowEvents"
          style={{
            whiteSpace: "wrap",
            overflow: "unset",
            textOverflow: "unset",
          }}
          key={row.id}
        >
          {row.createAt}
        </div>
      ),
      sortable: true,
      sortFunction: (a, b) => {
        const dateA = parse(a.createAt, "hh:mm:ss a dd/MM/yyyy", new Date());
        const dateB = parse(b.createAt, "hh:mm:ss a dd/MM/yyyy", new Date());
        return dateA - dateB;
      },
      reorder: true,
    },
    {
      id: 18,
      name: "Update At",
      cell: (row) => (
        <div
          data-tag="allowRowEvents"
          style={{
            whiteSpace: "wrap",
            overflow: "unset",
            textOverflow: "unset",
          }}
          key={row.id}
        >
          {row.updateAt}
        </div>
      ),
      sortable: true,
      sortFunction: (a, b) => {
        const dateA = parse(a.createAt, "hh:mm:ss a dd/MM/yyyy", new Date());
        const dateB = parse(b.createAt, "hh:mm:ss a dd/MM/yyyy", new Date());
        return dateA - dateB;
      },
      reorder: true,
    },
    {
      id: 19,
      name: "Option",
      style: "word-wrap:unset;word-break:unset;",
      cell: (row) => (
        <div style={{ display: "flex" }}>
          <Button
            variant="contained"
            color="primary"
            style={{ width: "50%" }}
            onClick={() => handleEdit(row)}
          >
            Edit
          </Button>
          <Button
            variant="contained"
            style={{ marginLeft: "20px", width: "50%" }}
            color="error"
            onClick={() => handleDelete(row.id)}
          >
            Delete
          </Button>
        </div>
      ),
    },
  ];

  const paginationComponentOptions = {
    selectAllRowsItem: true,
    selectAllRowsItemText: "ALL",
  };

  return (
    <div>
      <Loading open={isUploading} />
      <DataTable
        title="List Movie"
        columns={columns}
        data={movies}
        defaultSortFieldId={1}
        sortIcon={<ArrowDownward />}
        pagination
        paginationComponentOptions={paginationComponentOptions}
      />
      <Modal
        isOpen={isOpen}
        onRequestClose={closeModal}
        style={{
          overlay: {
            backgroundColor: "rgb(121 127 222 / 50%)",
          },
          content: {
            top: "50%",
            left: "50%",
            right: "auto",
            bottom: "auto",
            marginRight: "-50%",
            transform: "translate(-50%, -50%)",
            minWidth: "400px",
            maxWidth: "60%",
            padding: "20px",
            borderRadius: "8px",
            boxShadow: "0 3px 6px rgba(0, 0, 0, 0.1)",
            border: "2px solid red",
          },
        }}
        contentLabel="Description Modal"
      >
        <h2>Details</h2>
        <div
          style={{
            maxWitdh: "100",
            maxHeight: "500px",
            overflowY: "scroll",
            fontSize: "20px",
            color: "black",
            fontWeight: "200",
            marginBottom: "10px",
          }}
        >
          {modalContent}
        </div>
        <Button variant="contained" color="error" onClick={closeModal}>
          Close
        </Button>
      </Modal>
    </div>
  );
};